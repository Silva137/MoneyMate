package isel.pt.moneymate.services

import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.http.models.categories.CategoryDTO
import isel.pt.moneymate.http.models.categories.toDTO
import isel.pt.moneymate.http.models.users.UserDTO
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

    fun createWallet(walletInput: CreateWalletDTO, userId: Int): WalletDTO {
        val createdId = walletRepository.createWallet(walletInput.name, userId)
        return getWalletById(createdId)
    }

    fun getWalletById(walletId : Int): WalletDTO{
        val wallet = walletRepository.getWalletById(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")
        return wallet.toDTO()
    }

    fun getWalletsOfUser(userId: Int, offset: Int, limit: Int) : WalletsDTO {
        val wallets = walletRepository.getWalletsOfUser(userId, offset, limit)
            ?: throw NotFoundException("No wallets found for user with id $userId") //TODO
        return wallets.toDTO()
    }

    // TODO : check if its the owner of the wallet that is trying to update it ???
    fun updateWallet(walletInput: UpdateWalletDTO, walletId : Int) : WalletDTO {
        walletRepository.updateWallet(walletInput.name, walletId)
        val updatedWallet = walletRepository.getWalletById(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")
        return updatedWallet.toDTO()
    }

    // TODO: Receive user? Check if the wallet belongs to the user making the request
    // TODO : Ver se wallet e uma sharedWallet => Se sim nao pode apagar
    // TODO: Ver se existiam ligacoes user_sw_transaction e apagar também
    // Check if it is a shared Wallet (Has an entry on user asscoaitiano)
    fun deleteWallet(walletId : Int){
        transactionService.deleteTransactionsOfWallet(walletId)
        walletRepository.deleteWallet(walletId)
    }

}