package pt.isel.moneymate.services.users.models

data class RegisterInput(
    val username: String,
    val email: String,
    val password : String
)