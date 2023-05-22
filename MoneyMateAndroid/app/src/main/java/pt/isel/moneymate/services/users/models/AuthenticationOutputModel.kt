package pt.isel.moneymate.services.users.models

data class AuthenticationOutputModel(
    val access_token: String,
    val refresh_token: String
)
