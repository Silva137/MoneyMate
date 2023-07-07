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

    enum class StatisticsState {
        IDLE,
        GETTING_STATISTICS,
        FINISHED,
        ERROR
    }
}