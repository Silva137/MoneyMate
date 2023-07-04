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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StatisticsViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _categoriesBalancePos: List<CategoryBalanceDTO>? by mutableStateOf(emptyList())
    val categoriesBalancePos: List<CategoryBalanceDTO>? get() = _categoriesBalancePos

    private var _categoriesBalanceNeg: List<CategoryBalanceDTO>? by mutableStateOf(emptyList())
    val categoriesBalanceNeg: List<CategoryBalanceDTO>? get() = _categoriesBalanceNeg

    fun fetchCategoriesBalance(walletId : Int, startDate : LocalDate, endDate: LocalDate){
        viewModelScope.launch {
            try{
                val token = sessionManager.accessToken
                val response = Result.success(moneymateService.transactionsService.getCategoryBalance(token,walletId,startDate.toString(),endDate.toString()))
                _categoriesBalancePos = response.getOrNull()?.pos?.balanceList ?: emptyList()
                _categoriesBalanceNeg = response.getOrNull()?.neg?.balanceList ?: emptyList()
            }catch (e: Exception){
                Log.e("ERROR", "Failed to fetch transactions", e)
            }
        }
    }
}