package isel.pt.moneymate.controller.models

import jakarta.validation.constraints.NotBlank

data class CreateTransactionDTO (
    @field:NotBlank(message = "Transaction amount is required")
    val amount: Int,
    @field:NotBlank(message = "Transaction title is required")
    val title: String,
    //@field:Range(min = 1, max = 5, message = "Periodical must be an integer between 1 and 5")
    val periodical: Int
)

data class UpdateTransactionDTO (
    val categoryId: Int,
    val amount: Int,
    val title: String,
)