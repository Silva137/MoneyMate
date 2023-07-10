package pt.isel.moneymate.session

import android.content.Context

class SessionManagerSharedPrefs(val context: Context) : SessionManager {

    private val prefs by lazy {
        context.getSharedPreferences(SESSION_PREFS, Context.MODE_PRIVATE)
    }

    override val accessToken: String?
        get() = prefs.getString(ACCESS_TOKEN, null)

    override val refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)



    override fun setSession(
        accessToken: String?,
        refreshToken: String?
    ) {
        prefs.edit()
            .putString(ACCESS_TOKEN, accessToken)
            .putString(REFRESH_TOKEN, refreshToken)

            .apply()
    }

    override fun clearSession() {
        prefs.edit()
            .remove(ACCESS_TOKEN)
            .remove(REFRESH_TOKEN)
            .apply()
    }

    companion object {
        private const val SESSION_PREFS = "session"
        private const val ACCESS_TOKEN = "accessToken"
        private const val REFRESH_TOKEN = "refreshToken"
    }
}