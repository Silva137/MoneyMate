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
import pt.isel.moneymate.services.wallets.models.Wallet
import pt.isel.moneymate.session.SessionManager

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


    var selectedWalletId: Int by mutableStateOf(0)
         set


    fun fetchWallets(){
        viewModelScope.launch {
            _state = WalletState.GETTING_WALLETS
            try{
                val token = sessionManager.accessToken
                val walletsResponse = Result.success(moneymateService.walletsService.getWallets(token))
                _wallets = walletsResponse.getOrNull()?.wallets ?: emptyList()
                selectedWalletId = _wallets.firstOrNull()?.id ?: 0
                _state = WalletState.FINISHED
            }catch (e: Exception){
                Log.e("ERROR", "Failed to fetch wallets", e)
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val categoriesResponse = Result.success(moneymateService.categoriesService.getCategories(token))
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


    enum class WalletState {
        IDLE,
        GETTING_WALLETS,
        FINISHED
    }
}