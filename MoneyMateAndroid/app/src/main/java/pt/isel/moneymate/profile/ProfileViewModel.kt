package pt.isel.moneymate.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager

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
            _state= ProfileState.GETTING_USERS
            try {
                val token = sessionManager.accessToken
                val result = Result.success(moneymateService.usersService.getUsername(token))
                _username = result.getOrNull()?.username
                _state = ProfileState.FINISHED
            } catch (e: Exception) {
                _username = null
            }
            Log.v("USERNAME","$username")
        }
    }
}

enum class ProfileState {
    IDLE,
    GETTING_USERS,
    FINISHED
}