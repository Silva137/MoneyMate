package isel.pt.moneymate.services

import isel.pt.moneymate.controller.models.*
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.exceptions.InvalidParameterException
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val permitionsService: PermitionsService
) {
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

    fun getAllTransactions(
        user: User,
        walletId: Int,
        sortedBy: String,
        orderBy: String,
        startDate: Date,
        endDate: Date,
        offset: Int,
        limit: Int
    ): TransactionsDTO {
        return getTransactionsSortedBy(user.id, walletId, sortedBy, orderBy, startDate, endDate, offset, limit) { id, sortBy, orderBy, startDate, endDate, off, lim ->
            transactionRepository.getAllTransactions(id, sortBy, orderBy, startDate, endDate, off, lim)
        }
    }

    fun getIncomeTransactions(
        user: User,
        walletId: Int,
        sortedBy: String,
        orderBy: String,
        startDate: Date,
        endDate: Date,
        offset: Int,
        limit: Int
    ): TransactionsDTO {
        return getTransactionsSortedBy(user.id, walletId, sortedBy, orderBy, startDate, endDate, offset, limit) { id, sortBy, orderBy, startDate, endDate, off, lim ->
            transactionRepository.getIncomeTransactions(id, sortBy, orderBy,  startDate, endDate, off, lim)
        }
    }

    fun getExpenseTransactions(
        user: User,
        walletId: Int,
        sortedBy: String,
        orderBy: String,
        startDate: Date,
        endDate: Date,
        offset: Int,
        limit: Int
    ): TransactionsDTO {
        return getTransactionsSortedBy(user.id, walletId, sortedBy, orderBy, startDate, endDate, offset, limit) { id, sortBy, orderBy, startDate, endDate, off, lim ->
            transactionRepository.getExpenseTransactions(id, sortBy, orderBy,  startDate, endDate, off, lim)
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

    fun getByCategory(user: User, walletId: Int, categoryId: Int, startDate: Date, endDate: Date, offset: Int, limit: Int): TransactionsDTO {
        permitionsService.verifyUserOnWallet(user.id, walletId)
        val transactionsOfCategory = transactionRepository.getByCategory(walletId, categoryId, startDate, endDate, offset, limit)
            ?: throw NotFoundException("Transactions Of Category not Found")

        return transactionsOfCategory.toDTO()
    }

    fun getBalanceByCategory(user:User, walletId: Int, startDate: Date, endDate: Date,): CategoriesBalanceDTO {
        permitionsService.verifyUserOnWallet(user.id, walletId)
        val balanceOfCategories = transactionRepository.getBalanceByCategory(walletId, startDate, endDate)
            ?: throw NotFoundException("Balance of Categories not Found")

        return balanceOfCategories.toDTO()
    }

    fun getPosAndNegBalanceByCategory(user: User, walletId: Int, startDate: Date, endDate: Date,): PosAndNegCategoryBalanceDTO {
        permitionsService.verifyUserOnWallet(user.id, walletId)
        val negativeBalanceOfCategories = transactionRepository.getNegativeBalanceByCategory(walletId, startDate, endDate)
            ?: throw NotFoundException("Balance of Categories not Found")
        val positiveBalanceOfCategories = transactionRepository.getPositiveBalanceByCategory(walletId, startDate, endDate)
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
        userId: Int, walletId: Int, sortedBy: String, orderBy: String, startDate: Date, endDate: Date, offset: Int, limit: Int,
        getTransactionsFunction: (Int, String, String, Date, Date, Int, Int) -> List<Transaction>?
    ): TransactionsDTO {
        // Verify Parameters
        if (sortedBy !in validSortByValues || orderBy !in validOrderByValues)
            throw InvalidParameterException("Invalid parameters for sorting or ordering")

        // Verify if user as permitions in this wallet
        permitionsService.verifyUserOnWallet(userId, walletId)

        val sortedTransactions = getTransactionsFunction(walletId, sortedBy, orderBy, startDate, endDate, offset, limit)

        if(sortedTransactions.isNullOrEmpty())
            throw NotFoundException("Transactions of Wallet with id $walletId not found")

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