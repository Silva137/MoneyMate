package isel.pt.moneymate.http.models

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.moneymate.services.dtos.UserDTO
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/*
data class RegisterInputDTO(
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    val password: String
){
    fun toRegisterInputDTO() = UserDTO(username, email, password)
}


data class UserEditInputDTO(
    @field:NotBlank(message = "Username is required")
    val username: String
)

data class LoginInputDTO(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    val password: String
)

data class AuthenticationOutputDTO(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)


data class UserOutputDTO(
    @field:NotBlank(message = "Username is required")
    val username: String,
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String
)

data class UsersOutputDTO(
    @field:NotBlank(message = "Username is required")
    val users: List<UserOutputDTO>,
    val totalCount: Int = users.size
)

 */





