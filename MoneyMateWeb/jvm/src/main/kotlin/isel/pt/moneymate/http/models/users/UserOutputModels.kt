package isel.pt.moneymate.http.models.users

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank


data class AuthenticationOutDTO(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)

data class UserDTO(
    val id: Int,
    @field:NotBlank(message = "Username is required")
    val username: String,
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String
)

data class UsersDTO(
    val users: List<UserDTO>,
    @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
    val totalCount: Int = users.size
)





