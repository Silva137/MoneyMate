package pt.isel.moneymate.register
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.services.users.models.AuthenticationOutputModel
import pt.isel.moneymate.session.SessionManager

class RegisterViewModel(
    private val moneymateService: MoneyMateService,
) : ViewModel() {

    private var _authenticationState by mutableStateOf(AuthenticationState.IDLE)
    val authenticationState: AuthenticationState
        get() = _authenticationState

    fun register(username: String, password: String, email: String) {
        viewModelScope.launch {
            try {
                _authenticationState = AuthenticationState.LOADING
                moneymateService.usersService.register(username, password, email)
                _authenticationState = AuthenticationState.SUCCESS
            } catch (e: Exception) {
                _authenticationState = AuthenticationState.ERROR
            }
            Log.v("LOGIN", "Authentication state is $_authenticationState")
        }
    }

    /**
     * The state of the authentication process.
     */
    enum class AuthenticationState {
        IDLE,
        LOADING,
        SUCCESS,
        ERROR
    }
}

