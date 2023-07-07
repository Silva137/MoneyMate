package isel.pt.moneymate.controller.models

import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CreateRegularTransactionDTO(
    @field:Pattern(regexp = "^(daily|weekly|monthly|yearly)$", message = "Invalid frequency. Accepted values are daily, weekly, monthly, yearly.")
    val frquency: String,
    @field:NotBlank(message = "Transaction title is required")
    val title: String,
    @field:Digits(integer = 10, fraction = 2, message = "Transaction amount must be a number")
    val amount: Float,
)

data class UpdateRegularTransactionDTO(
    @field:Pattern(regexp = "^(daily|weekly|monthly|yearly)$", message = "Invalid frequency. Accepted values are daily, weekly, monthly, yearly.")
    val frquency: String,
    @field:NotBlank(message = "Transaction title is required")
    val title: String,
    @field:Digits(integer = 10, fraction = 2, message = "Transaction amount must be a number")
    val amount: Float,
    @field:Digits(integer = 10, fraction = 0, message = "Wallet Id must be a number")
    val walletId: Int,
    @field:Digits(integer = 10, fraction = 0, message = "Category Id must be a number")
    val categoryId: Int,
)




