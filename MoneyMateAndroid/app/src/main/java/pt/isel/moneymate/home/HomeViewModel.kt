package pt.isel.moneymate.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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

            }
        }

    }


    enum class WalletState {
        IDLE,
        GETTING_WALLETS,
        FINISHED
    }


}