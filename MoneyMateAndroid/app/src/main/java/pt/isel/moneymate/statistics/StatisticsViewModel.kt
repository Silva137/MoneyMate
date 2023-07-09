package pt.isel.moneymate.statistics

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
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.services.category.models.CategoryBalanceDTO
import pt.isel.moneymate.services.category.models.PosAndNegCategoryBalanceDTO
import pt.isel.moneymate.session.SessionManager
import pt.isel.moneymate.transactions.TransactionsViewModel
import pt.isel.moneymate.utils.APIResult
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StatisticsViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _state: StatisticsState by mutableStateOf(StatisticsState.IDLE)
    val state: StatisticsState get() = _state

    private var _categoriesBalancePos: List<CategoryBalanceDTO> by mutableStateOf(emptyList())
    val categoriesBalancePos: List<CategoryBalanceDTO> get() = _categoriesBalancePos

    private var _categoriesBalanceNeg: List<CategoryBalanceDTO> by mutableStateOf(emptyList())
    val categoriesBalanceNeg: List<CategoryBalanceDTO> get() = _categoriesBalanceNeg

    private var _categoryTransactionPos: List<Transaction> by mutableStateOf(emptyList())
    val categoryTransactionPos: List<Transaction> get() = _categoryTransactionPos

    private var _categoryTransactionNeg: List<Transaction> by mutableStateOf(emptyList())
    val categoryTransactionNeg: List<Transaction> get() = _categoryTransactionNeg

    private var _errorMessage by mutableStateOf<String?>(null)
    val errorMessage: String?
        get() = _errorMessage

    fun fetchCategoriesBalance(walletId: Int, startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            _state = StatisticsState.GETTING_STATISTICS
            try {
                val token = sessionManager.accessToken
                val response = moneymateService.transactionsService.getCategoryBalance(token,walletId,startDate.toString(),endDate.toString())
                when (response) {
                    is APIResult.Success -> {
                        _categoriesBalancePos = response.data.pos.balanceList
                        _categoriesBalanceNeg = response.data.neg.balanceList
                        _state = StatisticsState.FINISHED
                    }
                    is APIResult.Error -> {
                        Log.e("ERROR", "Failed to fetch Categories Balance: ")
                        _state = StatisticsState.ERROR
                    }
                }
            }catch (e: Exception){
                Log.e("ERROR", "Failed to fetch transactions", e)
            }
        }
    }

    fun fetchCategoryTransactions(walletId: Int, categoryId: Int, startDate: LocalDate, endDate: LocalDate) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        _categoryTransactionNeg = emptyList()
        _categoryTransactionPos = emptyList()
        viewModelScope.launch {
            _state = StatisticsState.GETTING_STATISTICS
            try {
                val token = sessionManager.accessToken
                val responsePos = moneymateService.transactionsService.getCategoryTransactionsPos(token,walletId,categoryId,startDate.toString(),endDate.toString())
                val responseNeg = moneymateService.transactionsService.getCategoryTransactionsNeg(token,walletId,categoryId,startDate.toString(),endDate.toString())
                when{
                    responsePos is APIResult.Success && responseNeg is APIResult.Success -> {
                        _categoryTransactionPos = responsePos.data.transactions.map { transactionDTO ->
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
                                LocalDateTime.parse(transactionDTO.createdAt.substring(0, 23), formatter)
                            )
                        }
                        _categoryTransactionNeg = responseNeg.data.transactions.map { transactionDTO ->
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
                                LocalDateTime.parse(transactionDTO.createdAt.substring(0, 23), formatter)
                            )
                        }
                        _state = StatisticsState.FINISHED
                    }
                    responsePos is APIResult.Error -> {
                        _categoryTransactionPos = emptyList()
                        Log.e("ERROR", "Failed to fetch Category Transactions: ")
                        _state = StatisticsState.ERROR
                    }
                    responseNeg is APIResult.Error -> {
                        _categoryTransactionNeg = emptyList()
                        Log.e("ERROR", "Failed to fetch Category Transactions: ")
                        _state = StatisticsState.ERROR
                    }
                }
            }catch (e: Exception){
                Log.e("ERROR", "Failed to fetch transactions", e)
            }
        }
    }

    enum class StatisticsState {
        IDLE,
        GETTING_STATISTICS,
        FINISHED,
        ERROR
    }
}

private fun convertType(amount : Float) : TransactionType {
    return if (amount >= 0) {
        TransactionType.INCOME
    } else {
        TransactionType.EXPENSE
    }
}