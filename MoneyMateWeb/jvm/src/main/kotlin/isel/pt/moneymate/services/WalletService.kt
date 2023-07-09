package isel.pt.moneymate.services

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.domain.Wallet
import isel.pt.moneymate.exceptions.AlreadyExistsException
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.exceptions.UnauthorizedException
import isel.pt.moneymate.http.models.users.UsersDTO
import isel.pt.moneymate.http.models.users.toDTO
import isel.pt.moneymate.http.models.wallets.*
import isel.pt.moneymate.http.utils.Consts.SHARED_WALLET_USER
import isel.pt.moneymate.repository.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class WalletService(
    private val walletRepository : WalletRepository,
    private val transactionService: TransactionService
) {

    // Same Request to both Wallets
    fun updateWallet(user: User, walletInput: UpdateWalletDTO, walletId : Int) : WalletWithBalanceDTO {
        val currentWallet = walletRepository.getWalletById(walletId)
        if (currentWallet != null) {
            return if (isPrivateWallet(currentWallet.user.id))
                updatePrivateWallet(user, walletInput, walletId)
            else updateSharedWallet(user, walletInput, walletId)
        }else
            throw NotFoundException("Wallet with id $walletId not found")
    }

    /** PRIVATE WALLET FUNCTIONS */
    fun getPrivateWalletById(walletId : Int): WalletWithBalanceDTO{
        val wallet = walletRepository.getWalletById(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")
        val balance = getWalletBalance(walletId)
        return wallet.toDTO(balance)
    }

    fun createPrivateWallet(walletInput: CreateWalletDTO, userId: Int): WalletWithBalanceDTO {
        verifyNameExistence(walletInput.name, userId)

        val createdId = walletRepository.createWallet(walletInput.name, userId)
        return getPrivateWalletById(createdId)
    }

    fun getPrivateWalletsOfUser(userId: Int, offset: Int, limit: Int) : WalletsWithBalanceDTO {
        val wallets = walletRepository.getPWOfUser(userId, offset, limit)
            ?: throw NotFoundException("No wallets found for user with id $userId") //TODO
        val map = wallets.associateWith { wallet -> getWalletBalance(wallet.id) }
        return map.toDTO()
    }

    fun updatePrivateWallet(user: User, walletInput: UpdateWalletDTO, walletId : Int) : WalletWithBalanceDTO {
        verifyUserInPW(user.id, walletId)
        verifyNameExistence(walletInput.name, user.id)

        walletRepository.updatePW(walletInput.name, walletId)
        return getPrivateWalletById(walletId)
    }

    fun deletePrivateWallet(user: User, walletId : Int){
        verifyUserInPW(user.id, walletId)
        deleteWallet(walletId)
    }

    /** SHARED WALLET FUNCTIONS */

    fun getSharedWalletById(userId: Int, walletId : Int): WalletWithBalanceDTO{
        val wallet = walletRepository.getWalletById(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")

        val walletName = walletRepository.getSWNameForUser(userId, walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")

        val resWallet = Wallet(wallet.id, walletName, wallet.user, wallet.createdAt)
        val balance = getWalletBalance(walletId)
        return resWallet.toDTO(balance)
    }

    fun createSharedWallet(walletInput: CreateWalletDTO, userId: Int): WalletWithBalanceDTO {
        verifyNameExistence(walletInput.name, userId)

        // Usefull to send on invite messages to have initial wallet Name
        val createdWalletId = walletRepository.createWallet(walletInput.name, SHARED_WALLET_USER) // Name em wallet fica com 'shared_wallet'
        walletRepository.createWalletUserAssociation(createdWalletId, userId, walletInput.name)
        return getSharedWalletById(userId, createdWalletId)
    }

    fun getSharedWalletsOfUser(userId: Int, offset: Int, limit: Int) : WalletsWithBalanceDTO {
        val wallets = walletRepository.getSWOfUser(userId, offset, limit)
            ?: throw NotFoundException("No shared wallets found for user with id $userId")
        val map = wallets.associateWith { wallet -> getWalletBalance(wallet.id) }
        return map.toDTO()
    }

    fun getMembersOfSW (user: User, sharedWalletId: Int): UsersDTO{
        verifyUserInSW(user.id, sharedWalletId)
        val users = walletRepository.getUsersOfSW(sharedWalletId)
            ?: throw NotFoundException("Users of wallet $sharedWalletId not found")
        return users.toDTO() // Ordenados por ordem de entrada na sharedWallet
    }

    fun updateSharedWallet(user: User, walletInput: UpdateWalletDTO, walletId : Int) : WalletWithBalanceDTO {
        verifyUserInSW(user.id, walletId)
        verifyNameExistence(walletInput.name, user.id)

        walletRepository.updateSW(walletInput.name, walletId, user.id)
        return getSharedWalletById(user.id, walletId)
    }


    fun deleteUserFromSharedWallet(user: User, walletId: Int){
        // Transações deste user continuam associadas
        walletRepository.deleteAssociation(user.id, walletId)
        // Remover transacoes da tabela de associações para privateWallets
        // TODO APAGAR INVITES QUE TENHAM ESTA WALLET
        if (!walletRepository.hasUsersInSW(walletId))
            deleteWallet(walletId) // Só apaga quando saem todos os users => Não existe pedido
    }

    /**
     * Aux
     */

    fun isPrivateWallet(userId: Int) = userId != SHARED_WALLET_USER && userId !=0

    fun verifyNameExistence(name: String, userId: Int){
        if (walletRepository.verifyNameExistence(name, userId)) {
            throw AlreadyExistsException("Wallet with that name already exists")
        }
    }

    /**
     * Verifies if user is the owner of that PrivateWallet
     */
    fun verifyUserInPW(userId: Int, walletId: Int) {
        val userOfWallet = walletRepository.getUserOfPW(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")

        if(userOfWallet != userId)
            throw UnauthorizedException("User does not have permission to perform this action on Wallet $walletId")
    }

    /**
     * Verifies if user is inserted in that sahredWallet
     */
    fun verifyUserInSW(userId: Int, walletId: Int) {
        val userOfWallet = walletRepository.getUserOfSW(userId, walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")

        if(userOfWallet != userId)
            throw UnauthorizedException("User does not have permission to perform this action on Wallet $walletId")
    }

    fun deleteWallet(walletId: Int){
        transactionService.deleteTransactionsOfWallet(walletId)
        walletRepository.deleteWallet(walletId)
    }

    fun getWalletBalance(walletId: Int): Int {
        return walletRepository.getWalletBalance(walletId)
    }
}


