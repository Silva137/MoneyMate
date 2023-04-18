package isel.pt.moneymate.services

import isel.pt.moneymate.controller.models.*
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class TransactionService(private val transactionRepository: TransactionRepository) {

    fun createTransaction(
        loggedUser: User,
        walletId: Int,
        categoryId: Int,
        transactionData: CreateTransactionDTO
    ): Int {
        return transactionRepository.createTransaction(
            loggedUser.id,
            walletId,
            categoryId,
            transactionData.amount,
            transactionData.title,
            transactionData.periodical,
        )
    }

    fun getTransactionById(transactionId: Int): Transaction? {
        return transactionRepository.getTransactionById(transactionId)
    }

    /**
     * Todos os campos sao opcionais mas caso o utilizador nao insira sao
     * submetidos os valores anteriores
     */
    fun updateTransaction(transactionId: Int, transactionData: UpdateTransactionDTO): Transaction? {
        // Todo verificacao e excessao de parametros
        transactionRepository.updateTransaction(
            transactionId,
            transactionData.categoryId,
            transactionData.amount,
            transactionData.title,
        )
        return getTransactionById(transactionId)
    }

    fun deleteTransaction(transactionId: Int){
        transactionRepository.deleteTransaction(transactionId)
    }

    fun getTransactionsFromWalletSortedBy(walletId: Int, criterion: String, order: String): List<Transaction>? {
        // Todo verificacao e excessao de parametros IllegalArgumentException
        return transactionRepository.getTransactionsSortedBy(walletId, criterion, order)
    }

    fun getSumsFromWallet(walletId: Int): List<Int> {
        // Todo verificacao e excessao de parametros
        return transactionRepository.getSumsFromWallet(walletId)

    }

    /** ----------------------------------- SW --------------------------------   */

    fun getTransactionsFromPWGivenCategory(walletId: Int, categoryId: Int): List<Transaction> {
        return transactionRepository.getTransactionsFromPWGivenCategory(walletId, categoryId)
    }

    fun getAmountsFromPwByCategory(walletId: Int): List<CategorySumsOutDto> {
        return transactionRepository.getAmountsFromPwByCategory(walletId)
    }

    /** ----------------------------------- SW --------------------------------   */

    fun getTransactionsFromSwGivenUser(walletId: Int, userId: Int): List<Transaction> {
        return transactionRepository.getTransactionsFromSwGivenUser(walletId, userId)
    }

    fun getAmountsFromSwByUser(walletId: Int): List<UserSumsOutDto> {
        return transactionRepository.getAmountsFromSwByUser(walletId)
    }



    /*


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

/*
        return when (criterion) {
            "bydate" ->
                when (order){
                    "asc" -> transactionRepository.getTransactionsByDateAsc(walletId)
                    "desc" -> transactionRepository.getTransactionsByDateDesc(walletId)
                    else -> null
                }
            "byprice" ->
                when (order){
                    "asc" -> transactionRepository.getTransactionsByPriceAsc(walletId)
                    "desc" -> transactionRepository.getTransactionsByPriceDesc(walletId)
                    else -> null
            }
            else -> null
        }

         */