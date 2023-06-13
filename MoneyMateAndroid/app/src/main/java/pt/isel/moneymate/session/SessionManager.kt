package pt.isel.moneymate.session


interface SessionManager {
    val accessToken: String?
    val refreshToken: String?
    val email: String?

    fun isLoggedIn(): Boolean = accessToken != null

    fun setSession(
        accessToken: String,
        refreshToken: String,
        email: String,
    )

    fun clearSession()
}