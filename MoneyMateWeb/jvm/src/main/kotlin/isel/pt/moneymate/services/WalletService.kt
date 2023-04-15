package isel.pt.moneymate.services

import isel.pt.moneymate.domain.Wallet
import isel.pt.moneymate.repository.WalletRepository
import isel.pt.moneymate.services.dtos.WalletDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
//TODO : Put all type of exceptions
class WalletService(private val walletRepository : WalletRepository) {

    //TODO :  Give a correct response, userID is hardcoded
    fun createWallet(walletInput: WalletDTO): Int {
        return walletRepository.createWallet(
            walletInput.name,
            1
        )
    }

    fun getWallets() : List<Wallet>{
        return walletRepository.getWallets(1)
    }

    fun updateWallet(walletInput: WalletDTO, walletId : Int) : Wallet {
        walletRepository.updateWallet(
            walletInput.name,
            walletId
        )
        return walletRepository.getWalletById(walletId)
    }

    fun deleteWallet(walletId : Int){
        walletRepository.deleteWallet(walletId = walletId)
    }

}