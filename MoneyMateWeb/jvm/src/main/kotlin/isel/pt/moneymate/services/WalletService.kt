package isel.pt.moneymate.services

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.exceptions.UnauthorizedException
import isel.pt.moneymate.http.models.wallets.*
import isel.pt.moneymate.repository.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class WalletService(
    private val walletRepository : WalletRepository,
    private val transactionService: TransactionService
) {

    fun createWallet(walletInput: CreateWalletDTO, userId: Int): WalletWithBalanceDTO {
        val createdId = walletRepository.createWallet(walletInput.name, userId)
        return getWalletById(createdId)
    }

    fun getWalletById(walletId : Int): WalletWithBalanceDTO{
        val wallet = walletRepository.getWalletById(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")
        val balance = getWalletBalance(walletId)
        return wallet.toDTO(balance)
    }

    fun getWalletsOfUser(userId: Int, offset: Int, limit: Int) : WalletsWithBalanceDTO {
        val wallets = walletRepository.getWalletsOfUser(userId, offset, limit)
            ?: throw NotFoundException("No wallets found for user with id $userId") //TODO
        val map = wallets.associateWith { wallet -> getWalletBalance(wallet.id) }
        return map.toDTO()
    }

    fun updateWallet(user: User, walletInput: UpdateWalletDTO, walletId : Int) : WalletWithBalanceDTO {
        verifyUserOnWallet(user.id, walletId)

        walletRepository.updateWallet(walletInput.name, walletId)
        val updatedWallet = walletRepository.getWalletById(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")
        val balance = getWalletBalance(walletId)
        return updatedWallet.toDTO(balance)
    }

    // TODO : Ver se wallet e uma sharedWallet => Se sim nao pode apagar
    // TODO: Ver se existiam ligacoes user_sw_transaction e apagar também
    // Check if it is a shared Wallet (Has an entry on user asscoaitiano)
    fun deleteWallet(user: User, walletId : Int){
        verifyUserOnWallet(user.id, walletId)
        transactionService.deleteTransactionsOfWallet(walletId)
        walletRepository.deleteWallet(walletId)
    }

    /**
     * Aux
     */
    fun getWalletBalance(walletId: Int): Int {
        return walletRepository.getWalletBalance(walletId)
    }

    fun verifyUserOnWallet(userId: Int, walletId: Int) {
        val userOfWallet = walletRepository.getUserOfWallet(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")

        if(userOfWallet != userId)
            throw UnauthorizedException("User does not have permission to perform this action on Wallet $walletId")
    }
}


