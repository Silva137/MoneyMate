package isel.pt.moneymate.services

import isel.pt.moneymate.controller.models.*
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class TransactionService(private val transactionRepository: TransactionRepository) {

    fun createTransaction(
        user: User,
        walletId: Int,
        categoryId: Int,
        transactionData: CreateTransactionDTO
    ): Int {
        return transactionRepository.createTransaction(
            user.id,
            walletId,
            categoryId,
            transactionData.amount,
            transactionData.title,
            transactionData.periodical,
        )
    }

    fun getTransactionById(transactionId: Int): TransactionDTO {
        val transaction = transactionRepository.getTransactionById(transactionId) ?: throw NotFoundException("Transaction with id $transactionId not found")
        return TransactionDTO(
            transaction.id,
            transaction.title,
            transaction.amount,
            transaction.user,
            transaction.wallet,
            transaction.category,
            transaction.createdAt,
            transaction.periodical,
        )
    }

    /**
     * Todos os campos sao opcionais mas caso o utilizador nao insira sao
     * submetidos os valores anteriores
     */
    fun updateTransaction(transactionId: Int, transactionData: UpdateTransactionDTO): TransactionDTO {
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

    fun getTransactionsFromWalletSortedBy(walletId: Int, criterion: String, order: String, offset: Int, limit: Int): TransactionsDTO {
        val sortedTransactions = transactionRepository.getTransactionsSortedBy(walletId, criterion, order, offset, limit) ?: throw NotFoundException("Transactions with Wallet id $walletId not found")
        val listDTO = sortedTransactions.map {
            TransactionDTO(
                it.id,
                it.title,
                it.amount,
                it.user,
                it.wallet,
                it.category,
                it.createdAt,
                it.periodical,
            )
        }
        return TransactionsDTO(listDTO)
    }

    fun getSumsFromWallet(walletId: Int): WalletBalanceDTO {
        // Todo verificacao e excessao de parametros
        return transactionRepository.getSumsFromWallet(walletId)

    }

    /** ----------------------------------- PW --------------------------------   */

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