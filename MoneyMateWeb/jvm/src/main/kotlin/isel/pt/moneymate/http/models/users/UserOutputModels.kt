package isel.pt.moneymate.http.models.users

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.moneymate.controller.models.TransactionsDTO
import isel.pt.moneymate.controller.models.toDTO
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.User
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
){
    constructor(user: User) : this(
        user.id,
        user._username,
        user.email
    )

}

fun User.toDTO() = UserDTO(this.id, this._username, this.email)

data class UsersDTO(
    val users: List<UserDTO>,
    @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
    val totalCount: Int = users.size
)

fun List<User>.toDTO(): UsersDTO {
    val users = this.map { it.toDTO() }
    return UsersDTO(users)
}





