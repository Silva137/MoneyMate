package isel.pt.moneymate.services

import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.repository.TransactionRepository
import isel.pt.moneymate.services.dtos.TransactionDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class TransactionService(private val transactionRepository: TransactionRepository) {

    fun createTransaction(userId: Int, walletId: Int, categoryId: Int, transactionDTO: TransactionDTO): Int {
        return transactionRepository.createTransaction(
            userId,
            walletId,
            categoryId,
            transactionDTO.amount,
            transactionDTO.title,
            transactionDTO.transactionType,
            transactionDTO.periodical,
        )
    }

    /*
    fun getTransactionById(transactionId: Int): Transaction {
        return transactionRepository.getTransactionById(transactionId)
    }

    fun updateTransaction(transactionDTO: TransactionDTO, walletId: Int): Transaction {
        return transactionRepository.updateTransaction(
            walletId,
            transactionDTO.amount,
            transactionDTO.title,
        )
    }

    fun deleteTransaction(transactionId: Int){
        transactionRepository.deleteTransaction(transactionId)
    }

    fun getTransactionsFromWalletOrdered(walletId: Int, criterion: String, order: Int): List<Transaction>? {
        // Todo passar order asc ord desc
        return when (criterion) {
            "bydate" -> transactionRepository.getTransactionsByDate(walletId)
            "byprice" -> transactionRepository.getTransactionsByPrice(walletId)
            else -> null
        }
    }

    fun getSumsFromWallet(walletId: Int, criterion: String): Int {
        return when (criterion) {
            "+" -> transactionRepository.getPositiveSumsFromWallet(walletId)
            "-" -> transactionRepository.getNegativeSumsFromWallet(walletId)
            else -> 0
        }
    }

    /** ----------------------------------- PW --------------------------------   */

    fun getTransactionsFromPWGivenCategory(walletId: Int, categoryId: String): List<Transaction> {
        return transactionRepository.getTransactionsFromPWGivenCategory(walletId, categoryId)
    }

    fun getAmountsFromPwByCategory(walletId: Int): Map<Int,Int> {
        return transactionRepository.getAmountsFromPwByCategory(walletId)
    }

    /** ----------------------------------- SW --------------------------------   */

    fun getTransactionsFromSwGivenUser(walletId: Int, userId: Int): List<Transaction> {
        return transactionRepository.getTransactionsFromSwGivenUser(walletId, userId)
    }

    fun getAmountsFromSwByUser(walletId: Int): Map<Int,Int> {
        return transactionRepository.getAmountsFromSwByUser(walletId)
    }

    /** ----------------------------------- OverView --------------------------------   */

    fun getSumsFromWallets(criterion: String): Int {
        return when (criterion) {
            "+" -> transactionRepository.getPositiveSumsFromWallets()
            "-" -> transactionRepository.getNegativeSumsFromWallets()
            else -> 0
        }
    }

    fun getTransactionsFromAllWalletsGivenCategory(categoryId: String): List<Transaction> {
        return transactionRepository.getTransactionsFromAllWalletsGivenCategory(categoryId)
    }

    fun getAmountsFromAllWalletsByCategory(): Map<Int, Int> {
        return transactionRepository.getAmountsFromAllWalletsByCategory()
    }*/
}