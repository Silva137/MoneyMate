package pt.isel.moneymate.services.users.models

data class LoginInput(
    val email: String,
    val password: String
)