package pt.isel.moneymate.profile

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.moneymate.login.LoginActivity
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager
import pt.isel.moneymate.session.SessionManagerSharedPrefs
import pt.isel.moneymate.utils.APIResult

class ProfileViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _username by mutableStateOf<String?>("")
    private var _state: ProfileState by mutableStateOf(ProfileState.IDLE)
    val username: String?
        get() = _username
    val state
        get() = _state

    fun getUsername() {
        viewModelScope.launch {
            _state = ProfileState.GETTING_USERS
            try {
                val token = sessionManager.accessToken
                val result = moneymateService.usersService.getUsername(token)
                if (result is APIResult.Success) {
                    _username = result.data.username
                    _state = ProfileState.FINISHED
                } else if (result is APIResult.Error) {
                    // Handle API error here
                }
            } catch (e: Exception) {
                _username = null
                // Handle exception here
            }
            Log.v("USERNAME", "$username")
        }
    }

    fun createWallet(walletName: String){
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val result = Result.success(moneymateService.walletsService.createWallet(token,walletName))
            } catch (e: Exception) {
            }
        }
    }


    fun logout(){
        viewModelScope.launch {
            sessionManager.clearSession()
            navigateToLogin()
        }
    }


    private fun navigateToLogin() {
        val context = (sessionManager as SessionManagerSharedPrefs).context
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

}

enum class ProfileState {
    IDLE,
    GETTING_USERS,
    FINISHED
}