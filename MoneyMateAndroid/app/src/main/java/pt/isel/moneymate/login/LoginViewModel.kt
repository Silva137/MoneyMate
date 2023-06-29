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

class LoginViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _authenticationState by mutableStateOf(AuthenticationState.IDLE)
    val authenticationState: AuthenticationState
        get() = _authenticationState

    private var _authenticationData by mutableStateOf<Result<AuthenticationOutputModel>?>(null)
    val authenticationData: Result<AuthenticationOutputModel>?
        get() = _authenticationData

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authenticationState = AuthenticationState.LOADING
                _authenticationData = Result.success(moneymateService.usersService.login(email, password))
                val outputModel: AuthenticationOutputModel = _authenticationData!!.getOrThrow()
                sessionManager.setSession(
                    accessToken = outputModel.access_token,
                    refreshToken = outputModel.refresh_token
                )
                _authenticationState = AuthenticationState.SUCCESS
            } catch (e: Exception) {
                _authenticationData = Result.failure(e)
                _authenticationState = AuthenticationState.ERROR
            }
            Log.v("LOGIN", "DATA IS ${_authenticationData}")
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



