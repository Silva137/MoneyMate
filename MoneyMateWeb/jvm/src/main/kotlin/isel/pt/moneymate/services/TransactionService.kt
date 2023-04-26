package isel.pt.moneymate.services

import isel.pt.moneymate.controller.models.*
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.exceptions.InvalidParameterException
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class TransactionService(private val transactionRepository: TransactionRepository) {

    fun createTransaction(
        transactionData: CreateTransactionDTO,
        user: User,
        categoryId: Int,
        walletId: Int
    ): TransactionDTO {
        val createdId = transactionRepository.createTransaction(
            transactionData.title,
            transactionData.amount,
            user.id,
            walletId,
            categoryId,
            LocalDateTime.now(),
            transactionData.periodical,
        )
        val transaction = getTransactionById(createdId)

        return TransactionDTO(
            transaction.id,
            transactionData.title,
            transactionData.amount,
            transaction.user,
            transaction.wallet,
            transaction.category,
            transaction.createdAt,
            transactionData.periodical
        )
    }

    fun getTransactionById(transactionId: Int): TransactionDTO {
        val transaction = transactionRepository.getTransactionById(transactionId) ?: throw NotFoundException("Transaction with id $transactionId not found")
        return TransactionDTO(
            transaction.id,
            transaction.title,
            transaction.amount,
            transaction.user.toDTO(),
            transaction.wallet.toDTO(),
            transaction.category.toDTO(),
            transaction.createdAt,
            transaction.periodical,
        )
    }


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

    fun getWalletTransactionsSortedBy(walletId: Int, sortedBy: String, orderBy: String, offset: Int, limit: Int): TransactionsDTO {
        val validSortByValues = setOf("bydate", "byprice"/*, "bycategory"*/)
        val validOrderByValues = setOf("ASC", "DESC")
        if (sortedBy !in validSortByValues || orderBy !in validOrderByValues)
            throw InvalidParameterException("Invalid parameters for sorting or ordering")

        val sortedTransactions = transactionRepository.getTransactionsSortedBy(walletId, sortedBy, orderBy, offset, limit)
            ?: throw NotFoundException("Transactions of Wallet with id $walletId not found")
        val listDTO = sortedTransactions.map {
            TransactionDTO(it.id, it.title, it.amount, it.user.toDTO(), it.wallet.toDTO(), it.category.toDTO(), it.createdAt, it.periodical)
        }
        return TransactionsDTO(listDTO)
    }

    fun getWalletBalance(walletId: Int): WalletBalanceDTO {
        return transactionRepository.getWalletBalance(walletId)
    }

    /** ----------------------------------- PW --------------------------------   */

    fun getPWTransactionsByCategory(walletId: Int, categoryId: Int): TransactionsDTO {
        val categoryTransactions = transactionRepository.getPWTransactionsByCategory(walletId, categoryId)
        val listDTO = categoryTransactions.map {
            TransactionDTO(it.id, it.title, it.amount, it.user.toDTO(), it.wallet.toDTO(), it.category.toDTO(), it.createdAt, it.periodical)
        }
        return TransactionsDTO(listDTO)
    }

    fun getPWCategoriesBalance(walletId: Int): List<CategoriesBalance> {
        return transactionRepository.getPWCategoriesBalance(walletId)
    }

    /** ----------------------------------- SW --------------------------------   */

    fun getSWTransactionsByUser(walletId: Int, userId: Int): List<Transaction> {
        return transactionRepository.getSWTransactionsByUser(walletId, userId)
    }

    fun getSWUsersBalance(walletId: Int): List<UserSumsOutDto> {
        return transactionRepository.getSWUsersBalance(walletId)
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