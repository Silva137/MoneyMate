package pt.isel.moneymate.session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SessionManagerInMemory : SessionManager {
    private var _accessToken: String? by mutableStateOf(null)
    private var _refreshToken: String? by mutableStateOf(null)
    private var _username: String? by mutableStateOf(null)


    override val accessToken
        get() = _accessToken

    override val refreshToken
        get() = _refreshToken

    override val username
        get() = _username

    override fun setSession(
        accessToken: String,
        refreshToken: String,
        username: String,
    ) {
        this._accessToken = accessToken
        this._refreshToken = refreshToken
        this._username = username
    }
    override fun clearSession() {
        this._accessToken = null
        this._refreshToken = null
        this._username = null
    }
}