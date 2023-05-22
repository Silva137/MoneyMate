package pt.isel.moneymate.session


interface SessionManager {
    val accessToken: String?
    val refreshToken: String?
    val username: String?

    fun isLoggedIn(): Boolean = accessToken != null

    fun setSession(
        accessToken: String,
        refreshToken: String,
        username: String,
    )

    fun clearSession()
}