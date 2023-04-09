package isel.pt.moneymate.domain

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val passwordHash: String,
)