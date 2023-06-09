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
    private val validSortByValues = setOf("bydate", "byprice"/*, "bycategory"*/)
    private val validOrderByValues = setOf("ASC", "DESC")


    /** ----------------------------------- Transactions --------------------------------   */


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
        return getTransactionById(createdId)
    }

    fun getTransactionById(transactionId: Int): TransactionDTO {
        val transaction = transactionRepository.getTransactionById(transactionId)
            ?: throw NotFoundException("Transaction with id $transactionId not found")
        return transaction.toDTO()
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

    // TODO : Apgar tambem user_shared_waleet_association
    fun deleteTransaction(transactionId: Int) {
        transactionRepository.deleteTransaction(transactionId)
    }

    fun getAllTransactions(walletId: Int, sortedBy: String, orderBy: String, offset: Int, limit: Int): TransactionsDTO {
        return getTransactionsSortedBy(walletId, sortedBy, orderBy, offset, limit) { id, sortBy, orderBy, off, lim ->
            transactionRepository.getAllTransactions(id, sortBy, orderBy, off, lim)
        }
    }

    fun getIncomeTransactions(
        walletId: Int,
        sortedBy: String,
        orderBy: String,
        offset: Int,
        limit: Int
    ): TransactionsDTO {
        return getTransactionsSortedBy(walletId, sortedBy, orderBy, offset, limit) { id, sortBy, orderBy, off, lim ->
            transactionRepository.getIncomeTransactions(id, sortBy, orderBy, off, lim)
        }
    }

    fun getExpenseTransactions(
        walletId: Int,
        sortedBy: String,
        orderBy: String,
        offset: Int,
        limit: Int
    ): TransactionsDTO {
        return getTransactionsSortedBy(walletId, sortedBy, orderBy, offset, limit) { id, sortBy, orderBy, off, lim ->
            transactionRepository.getExpenseTransactions(id, sortBy, orderBy, off, lim)
        }
    }

    fun getWalletBalanceOld(walletId: Int): WalletBalanceDTO {
        val walletBalance = transactionRepository.getWalletBalanceOld(walletId)
            ?: throw NotFoundException("Balance of Wallet not found")
        return walletBalance.toDTO()
    }

    /*
    fun getWalletBalance(walletId: Int): WalletBalanceDTO {
        val walletBalance = transactionRepository.getWalletBalance(walletId)
            ?: throw NotFoundException("Balance of Wallet not found")
        return walletBalance.toDTO()
    }
    */
    /** ----------------------------------- PW --------------------------------   */

    fun getByCategory(walletId: Int, categoryId: Int, offset: Int, limit: Int): TransactionsDTO {
        val transactionsOfCategory = transactionRepository.getByCategory(walletId, categoryId, offset, limit)
            ?: throw NotFoundException("Transactions Of Category not Found")
        return transactionsOfCategory.toDTO()
    }

    fun getBalanceByCategory(walletId: Int): CategoriesBalanceDTO {
        val balanceOfCategories = transactionRepository.getBalanceByCategory(walletId)
            ?: throw NotFoundException("Balance of Categories not Found")
        return balanceOfCategories.toDTO()
    }

    fun getPosAndNegBalanceByCategory(walletId: Int): PosAndNegCategoryBalanceDTO {
        val negativeBalanceOfCategories = transactionRepository.getNegativeBalanceByCategory(walletId)
            ?: throw NotFoundException("Balance of Categories not Found")
        val positiveBalanceOfCategories = transactionRepository.getPositiveBalanceByCategory(walletId)
            ?: throw NotFoundException("Balance of Categories not Found")
        val negDTO = negativeBalanceOfCategories.toDTO()
        val posDTO = positiveBalanceOfCategories.toDTO()
        return PosAndNegCategoryBalanceDTO(negDTO,posDTO)
    }

    /** ----------------------------------- OverView --------------------------------   */

    fun getAllByCategory(categoryId: Int, offset: Int, limit: Int): TransactionsDTO {
        val transactionsOfCategory = transactionRepository.getAllByCategory(categoryId, offset, limit)
            ?: throw NotFoundException("Transactions Of Category not Found")
        return transactionsOfCategory.toDTO()

    }

    fun getAllBalanceByCategory(): CategoriesBalanceDTO {
        val balanceOfCategories = transactionRepository.getAllBalanceByCategory()
            ?: throw NotFoundException("Balance of Categories not Found")
        return balanceOfCategories.toDTO()
    }

    /** ----------------------------------- SW --------------------------------   */

    fun getByUser(walletId: Int, userId: Int, offset: Int, limit: Int): TransactionsDTO {
        val transactionsOfUser = transactionRepository.getByUser(walletId, userId, offset, limit)
            ?: throw NotFoundException("Transactions Of User not Found")
        return transactionsOfUser.toDTO()
    }

    fun getBalanceByUser(walletId: Int): UsersBalanceDTO {
        val balanceOfCategories = transactionRepository.getBalanceByUser(walletId)
            ?: throw NotFoundException("Balance of Categories not Found")
        return balanceOfCategories.toDTO()
    }

    /** ----------------------------------- Regular --------------------------------   */

    fun getPeriodicalTransactions(offset: Int, limit: Int): TransactionsDTO {
        val sortedTransactions = transactionRepository.getPeriodicalTransactions(offset, limit)
            ?: throw NotFoundException("Peridical Transactions not found")
        return sortedTransactions.toDTO()
    }

    fun updateTransactionFrequency(transactionId: Int, transactionData: UpdateTransactionFrequencyDTO): TransactionDTO {
        transactionRepository.updateTransactionFrequency(
            transactionId,
            transactionData.periodical,
        )
        return getTransactionById(transactionId)
    }

    fun updateTransactionAmount(transactionId: Int, transactionData: UpdateTransactionAmountDTO): TransactionDTO {
        transactionRepository.updateTransactionAmount(
            transactionId,
            transactionData.amount,
        )
        return getTransactionById(transactionId)
    }

    /** ----------------------------------- Auxiliar Functions --------------------------------   */


    private fun getTransactionsSortedBy(
        walletId: Int, sortedBy: String, orderBy: String, offset: Int, limit: Int,
        getTransactionsFunction: (Int, String, String, Int, Int) -> List<Transaction>?
    ): TransactionsDTO {
        if (sortedBy !in validSortByValues || orderBy !in validOrderByValues)
            throw InvalidParameterException("Invalid parameters for sorting or ordering")

        val sortedTransactions = getTransactionsFunction(walletId, sortedBy, orderBy, offset, limit)
            ?: throw NotFoundException("Transactions of Wallet with id $walletId not found")

        return sortedTransactions.toDTO()
    }

    fun updateTransactionsCategories(userId: Int, oldCategoryId: Int, newCategoryId: Int) {
       if(oldCategoryId == newCategoryId) return
        transactionRepository.updateTransactionsCategories(userId, oldCategoryId, newCategoryId)
    }

    fun deleteTransactionsOfWallet(walletId: Int) {
        transactionRepository.deleteTransactionsOfWallet(walletId)
    }
}