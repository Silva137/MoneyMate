package isel.pt.moneymate.http.models.wallets

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.http.models.users.UserDTO
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

data class WalletDTO (
    @field:NotBlank(message = "Wallet name is required")
    val name: String,
    @field:NotNull(message = "User is required")
    val user: UserDTO,
    @field:DateTimeFormat //(pattern = "dd-MM-yyyy")
    val createdAt: Date
)


data class WalletsDTO (
    val categories: List<WalletDTO>,
    @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
    val totalCount: Int = categories.size
)