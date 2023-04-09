package isel.pt.moneymate.services.dtos

data class RegisterInputDTO(
    val username: String,
    val email: String,
    val password: String
)