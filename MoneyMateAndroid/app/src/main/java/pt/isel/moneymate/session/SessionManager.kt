package pt.isel.moneymate.session


interface SessionManager {
    val accessToken: String?
    val refreshToken: String?

    fun isLoggedIn(): Boolean = accessToken != null

    fun setSession(
        accessToken: String?,
        refreshToken: String?
    )

    fun clearSession() = setSession(null, null)
}