package isel.pt.moneymate.services

import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.http.models.wallets.CreateWalletDTO
import isel.pt.moneymate.http.models.wallets.UpdateWalletDTO
import isel.pt.moneymate.http.models.wallets.WalletDTO
import isel.pt.moneymate.http.models.wallets.WalletsDTO
import isel.pt.moneymate.repository.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class WalletService(private val walletRepository : WalletRepository) {

    fun createWallet(walletInput: CreateWalletDTO, userId: Int): Int {
        return walletRepository.createWallet(walletInput.name, userId)
    }

    fun getWalletsOfUser(userId: Int, offset: Int, limit: Int) : WalletsDTO {
        val wallets = walletRepository.getWalletsOfUser(userId, offset, limit) ?: throw NotFoundException("No wallets found for user with id $userId") //TODO
        val listDTO = wallets.map { WalletDTO(it.id, it.name, it.user.toDTO(), it.createdAt) }
        return WalletsDTO(listDTO)
    }

    //check if its the owner of the wallet that is trying to update it ???
    fun updateWallet(walletInput: UpdateWalletDTO, walletId : Int) : WalletDTO {
        walletRepository.updateWallet(walletInput.name, walletId)
        val updatedWallet = walletRepository.getWalletById(walletId) ?: throw NotFoundException("Wallet with id $walletId not found")
        return WalletDTO(updatedWallet.id, updatedWallet.name, updatedWallet.user.toDTO(), updatedWallet.createdAt)
    }

    fun deleteWallet(walletId : Int){
        walletRepository.deleteWallet(walletId)
    }
}