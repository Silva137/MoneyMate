package pt.isel.moneymate.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.login.LoginViewModel
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.services.transactions.models.WalletBalanceDTO
import pt.isel.moneymate.services.wallets.models.Wallet
import pt.isel.moneymate.session.SessionManager
import pt.isel.moneymate.utils.getCurrentMonthRange
import pt.isel.moneymate.utils.APIResult
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

    private var _errorMessage by mutableStateOf<String?>(null)
    val errorMessage: String?
        get() = _errorMessage

    var selectedWalletId: Int by mutableStateOf(0)
        set


    fun fetchWallets() {
        viewModelScope.launch {
            _state = WalletState.GETTING_WALLETS
            try {
                val token = sessionManager.accessToken
                val walletsResponse = moneymateService.walletsService.getWallets(token)

                when (walletsResponse) {
                    is APIResult.Success -> {
                        _wallets = walletsResponse.data.wallets

                        if (selectedWalletId == 0) {
                            selectedWalletId = _wallets.firstOrNull()?.id ?: 0
                        }
                        _state = WalletState.FINISHED
                    }
                    is APIResult.Error -> {
                        val errorMessage = walletsResponse.message
                        _errorMessage = errorMessage
                        _state = WalletState.ERROR
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to fetch wallets", e)
            }
        }
    }




    fun createTransaction(walletId: Int, categoryId: Int, amount: Float, title: String) {
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val response = moneymateService.transactionsService.createTransaction(token, categoryId, walletId, amount, title)
                when(response){
                    is APIResult.Success ->  fetchWallets()

                    is APIResult.Error -> {
                        val errorMessage = response.message
                        _errorMessage = errorMessage
                        _state = WalletState.ERROR
                    }
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
                val walletResponse = moneymateService.walletsService.getWalletBalance(
                    token,
                    selectedWalletId,
                    dateRange.first,
                    dateRange.second
                )

                when (walletResponse) {
                    is APIResult.Success -> {
                        val incomeSum = walletResponse.data.incomeSum ?: 0
                        val expenseSum = walletResponse.data.expenseSum ?: 0
                        _balance = WalletBalanceDTO(incomeSum, expenseSum)
                        Log.v("BALANCE", "$_balance")
                    }
                    is APIResult.Error -> {
                            val errorMessage = walletResponse.message
                            _errorMessage = errorMessage
                            _state = WalletState.ERROR
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to get wallet balance", e)
            }
        }
    }
    enum class WalletState {
        IDLE,
        GETTING_WALLETS,
        FINISHED,
        ERROR
    }
}







