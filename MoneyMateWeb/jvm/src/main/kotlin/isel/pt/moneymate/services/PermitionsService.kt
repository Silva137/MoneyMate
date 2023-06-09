package isel.pt.moneymate.services

import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.exceptions.UnauthorizedException
import isel.pt.moneymate.http.models.wallets.*
import isel.pt.moneymate.repository.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class PermitionsService(
    private val walletRepository : WalletRepository,

) {

    fun verifyUserOnWallet(userId: Int, walletId: Int) {
        val userOfWallet = walletRepository.getUserOfWallet(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")

        if(userOfWallet != userId)
            throw UnauthorizedException("User does not have permission to perform this action")
    }

    fun verifyCategory(categoryId: Int){
        //if(categoryId == 1000)
          //  throw UnauthorizedException("User does not have permission to perform this action")

    }
}


