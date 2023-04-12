package isel.pt.moneymate.services

import isel.pt.moneymate.domain.Wallet
import isel.pt.moneymate.repository.WalletRepository
import isel.pt.moneymate.services.dtos.CreateWalletDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
//TODO : Put all type of exceptions
class WalletService(private val walletRepository : WalletRepository) {

    //TODO :  Give a correct response, userID is hardcoded
    fun createWallet(walletInput: CreateWalletDTO): Int {
        return walletRepository.createWallet(walletName = walletInput.name, userId = 1)
    }

    fun getWallets() : List<Wallet>{
        return walletRepository.getWallets(userId = 1)
    }

    fun updateWallet(walletInput: CreateWalletDTO, walletId : Int) : Wallet {
        walletRepository.updateWallet(newName = walletInput.name,walletId= walletId)
        return walletRepository.getWalletById(walletId = walletId)
    }

    fun deleteWallet(walletId : Int){
        walletRepository.deleteWallet(walletId = walletId)
    }

}