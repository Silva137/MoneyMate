package pt.isel.moneymate.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.moneymate.domain.Category

import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.services.transactions.models.WalletBalanceDTO
import pt.isel.moneymate.services.wallets.models.Wallet
import pt.isel.moneymate.session.SessionManager
import pt.isel.moneymate.utils.getCurrentMonthRange
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class HomeViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _state: WalletState by mutableStateOf(WalletState.IDLE)
    val state: WalletState get() = _state

    private var _wallets: List<Wallet> by mutableStateOf(emptyList())
    val wallets: List<Wallet> get() = _wallets

    private var _categories: List<Category> by mutableStateOf(emptyList())
    val categories: List<Category> get() = _categories

    private var _balance: WalletBalanceDTO by mutableStateOf(WalletBalanceDTO(0, 0))
    val balance: WalletBalanceDTO get() = _balance


    var selectedWalletId: Int by mutableStateOf(0)
        set


    fun fetchWallets() {
        viewModelScope.launch {
            _state = WalletState.GETTING_WALLETS
            try {
                val token = sessionManager.accessToken
                val walletsResponse = Result.success(moneymateService.walletsService.getWallets(token))
                _wallets = walletsResponse.getOrNull()?.wallets ?: emptyList()

                if (selectedWalletId == 0) {
                    selectedWalletId = _wallets.firstOrNull()?.id ?: 0
                }
                _state = WalletState.FINISHED
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to fetch wallets", e)
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val categoriesResponse =
                    Result.success(moneymateService.categoriesService.getCategories(token))
                val bothCategoriesDTO = categoriesResponse.getOrNull()

                _categories = if (bothCategoriesDTO != null) {
                    val userCategories = bothCategoriesDTO.userCategories.categories
                    val systemCategories = bothCategoriesDTO.systemCategories.categories

                    val combinedCategories = userCategories + systemCategories
                    combinedCategories
                } else {
                    emptyList()
                }
                Log.v("CATEGORIES", _categories.toString())
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to fetch wallets", e)
            }
        }
    }
    fun createTransaction(walletId: Int, categoryId: Int, amount: Float, title: String) {
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val response = Result.success(moneymateService.transactionsService.createTransaction(token, categoryId, walletId, amount, title))
                if(response.isSuccess){
                    fetchWallets()
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to create transaction", e)
            }
        }
    }


    fun getWalletBalance() {
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val dateRange = getCurrentMonthRange()
                val walletResponse = Result.success(
                    moneymateService.walletsService.getWalletBalance(
                        token,
                        selectedWalletId,
                        dateRange.first,
                        dateRange.second
                    )
                )
                _balance = WalletBalanceDTO(walletResponse.getOrNull()?.incomeSum ?: 0,walletResponse.getOrNull()?.expenseSum ?: 0)
                Log.v("BALANCE","${_balance}")

            } catch (e: Exception) {
                Log.e("ERROR", "Failed to getBalance ", e)
            }
        }
    }
    enum class WalletState {
        IDLE,
        GETTING_WALLETS,
        FINISHED
    }
}







