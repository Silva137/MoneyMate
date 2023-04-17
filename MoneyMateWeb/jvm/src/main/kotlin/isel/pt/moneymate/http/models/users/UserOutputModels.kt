package isel.pt.moneymate.http.models.users

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank


data class AuthenticationOutDTO(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)


data class GetUserDTO(
    @field:NotBlank(message = "Username is required")
    val username: String,
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String
)

data class GetUsersDTO(
    @field:NotBlank(message = "Username is required")
    val users: List<GetUserDTO>,
    val totalCount: Int = users.size
)





