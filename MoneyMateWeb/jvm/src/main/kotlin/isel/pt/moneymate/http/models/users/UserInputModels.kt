package isel.pt.moneymate.http.models.users

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class CreateUserDTO(
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    val password: String
)

data class UpdateUserDTO(
    @field:NotBlank(message = "Username is required")
    val username: String
)

data class LoginUserDTO(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    val password: String
)

data class RefreshTokenDTO(
    @field:NotBlank(message = "Refresh token is required")
    val refreshToken: String
)





