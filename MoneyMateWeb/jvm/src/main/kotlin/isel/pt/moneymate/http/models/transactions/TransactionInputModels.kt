package isel.pt.moneymate.controller.models

import isel.pt.moneymate.domain.Transaction
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank

data class CreateTransactionDTO(
    @field:Digits(integer = 10, fraction = 2, message = "Transaction amount must be a number")
    val amount: Float,
    @field:NotBlank(message = "Transaction title is required")
    val title: String,
    //@field:Range(min = 1, max = 5, message = "Periodical must be an integer between 1 and 5")
    val periodical: Int
)

data class UpdateTransactionDTO(
    @field:Digits(integer = 10, fraction = 0, message = "Category Id must be a number")
    val categoryId: Int,
    @field:Digits(integer = 10, fraction = 2, message = "Transaction amount must be a number")
    val amount: Float,
    @field:NotBlank(message = "Transaction title is required")
    val title: String,
)

data class UpdateTransactionFrequencyDTO(
    val periodical: Int,

)

data class UpdateTransactionAmountDTO(
    @field:Digits(integer = 10, fraction = 2, message = "Transaction amount must be a number")
    val amount: Float,
)



