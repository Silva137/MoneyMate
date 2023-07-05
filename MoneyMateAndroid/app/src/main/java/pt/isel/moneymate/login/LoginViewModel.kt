package pt.isel.moneymate.login

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
import pt.isel.moneymate.utils.APIResult

class LoginViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _authenticationState by mutableStateOf(AuthenticationState.IDLE)
    val authenticationState: AuthenticationState
        get() = _authenticationState

    private var _authenticationData by mutableStateOf<APIResult<AuthenticationOutputModel>?>(null)
    val authenticationData: APIResult<AuthenticationOutputModel>?
        get() = _authenticationData

    private var _errorMessage by mutableStateOf<String?>(null)
    val errorMessage: String?
        get() = _errorMessage

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authenticationState = AuthenticationState.LOADING
                _authenticationData = moneymateService.usersService.login(email, password)
                when (_authenticationData) {
                    is APIResult.Success -> {
                        val outputModel = (_authenticationData as APIResult.Success<AuthenticationOutputModel>).data
                        sessionManager.setSession(
                            accessToken = outputModel.access_token,
                            refreshToken = outputModel.refresh_token
                        )
                        _authenticationState = AuthenticationState.SUCCESS
                    }
                    is APIResult.Error -> {
                        val errorMessage = (_authenticationData as APIResult.Error).message
                        _errorMessage = errorMessage
                        _authenticationState = AuthenticationState.ERROR
                    }
                }
            } catch (e: Exception) {
                // Exception occurred during login
                _authenticationData = APIResult.Error(e.message ?: "An error occurred during login")
                _errorMessage = e.message ?: "An error occurred during login"
                _authenticationState = AuthenticationState.ERROR
            }
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



