package pt.isel.moneymate.session

import android.content.Context

class SessionManagerSharedPrefs(private val context: Context) : SessionManager {

    private val prefs by lazy {
        context.getSharedPreferences(SESSION_PREFS, Context.MODE_PRIVATE)
    }

    override val accessToken: String?
        get() = prefs.getString(ACCESS_TOKEN, null)

    override val refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)

    override val username: String?
        get() = prefs.getString(USERNAME, null)


    override fun setSession(
        accessToken: String,
        refreshToken: String,
        username: String,
    ) {
        prefs.edit()
            .putString(ACCESS_TOKEN, accessToken)
            .putString(REFRESH_TOKEN, refreshToken)
            .putString(USERNAME, username)

            .apply()
    }

    override fun clearSession() {
        prefs.edit()
            .remove(ACCESS_TOKEN)
            .remove(REFRESH_TOKEN)
            .remove(USERNAME)
            .apply()
    }

    companion object {
        private const val SESSION_PREFS = "session"
        private const val ACCESS_TOKEN = "accessToken"
        private const val REFRESH_TOKEN = "refreshToken"
        private const val USERNAME = "username"
    }
}