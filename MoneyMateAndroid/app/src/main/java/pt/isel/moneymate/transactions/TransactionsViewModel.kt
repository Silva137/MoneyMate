package pt.isel.moneymate.transactions

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.domain.Transaction
import pt.isel.moneymate.domain.TransactionType
import pt.isel.moneymate.home.HomeViewModel
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager
import pt.isel.moneymate.utils.APIResult
import pt.isel.moneymate.utils.getCurrentYearRange
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionsViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _state: TransactionState by mutableStateOf(TransactionState.IDLE)
    val state: TransactionState get() = _state

    private var _transactions: List<Transaction> by mutableStateOf(emptyList())
    val transactions: List<Transaction> get() = _transactions

    private var _errorMessage by mutableStateOf<String?>(null)
    val errorMessage: String?
        get() = _errorMessage

    fun fetchTransactions(
        walletId: Int,
        startDate: LocalDate,
        endDate: LocalDate,
        sortedBy: String,
        orderBy: String
    ) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        viewModelScope.launch {
            _state = TransactionState.GETTING_TRANSACTIONS
            try {
                val token = sessionManager.accessToken
                val response = moneymateService.transactionsService.getWalletTransactions(
                    token,
                    walletId,
                    startDate.toString(),
                    endDate.toString(),
                    sortedBy,
                    orderBy
                )
                when (response) {
                    is APIResult.Success -> {
                        _transactions = response.data.transactions.map { transactionDTO ->
                            Transaction(
                                transactionDTO.id,
                                convertType(transactionDTO.amount),
                                transactionDTO.title,
                                transactionDTO.amount.toDouble(),
                                Category(
                                    transactionDTO.category.id,
                                    transactionDTO.category.name,
                                    transactionDTO.category.user
                                ),
                                LocalDateTime.parse(transactionDTO.createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                            )
                        }
                        _state = TransactionState.FINISHED
                    }
                    is APIResult.Error -> {
                        val errorMessage = response.message
                        _errorMessage = errorMessage
                        _transactions = emptyList()
                        _state = TransactionState.ERROR
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to fetch transactions", e)
            }
        }
    }

    fun updateTransaction(
        transactionId: Int,
        updatedName: String,
        amount: String,
        categoryId: Int,
        selectedWallet: Int
    ) {
        viewModelScope.launch {
            val dataRange = getCurrentYearRange()
            try {
                val token = sessionManager.accessToken
                val response = moneymateService.transactionsService.updateTransaction(
                    token,
                    updatedName,
                    transactionId,
                    amount.toFloat(),
                    categoryId
                )
                when (response) {
                    is APIResult.Success -> {
                        fetchTransactions(
                            selectedWallet,
                            dataRange.first,
                            dataRange.second,
                            "bydate",
                            "DESC"
                        )
                    }
                    is APIResult.Error -> {
                        val errorMessage = response.message
                        _errorMessage = errorMessage
                        _transactions = emptyList()
                        _state = TransactionState.ERROR
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to update transaction", e)
            }
        }
    }


    fun deleteTransaction(transactionId: Int, selectedWallet: Int) {
        viewModelScope.launch {
            val dataRange = getCurrentYearRange()
            try {
                val token = sessionManager.accessToken
                val response = moneymateService.transactionsService.deleteTransaction(token,transactionId)
                when (response) {
                    is APIResult.Success -> {
                        fetchTransactions(
                            selectedWallet,
                            dataRange.first,
                            dataRange.second,
                            "bydate",
                            "DESC"
                        )
                    }
                    is APIResult.Error -> {
                        val errorMessage = response.message
                        _errorMessage = errorMessage
                        _transactions = emptyList()
                        _state = TransactionState.ERROR
                    }
                }
            }catch(e: Exception){
                Log.e("ERROR", "Failed to delete transaction", e)
            }

        }
    }


    private fun convertType(amount: Float): TransactionType {
        return if (amount >= 0) {
            TransactionType.INCOME
        } else {
            TransactionType.EXPENSE
        }
    }

    enum class TransactionState {
        IDLE,
        GETTING_TRANSACTIONS,
        FINISHED,
        ERROR
    }
}