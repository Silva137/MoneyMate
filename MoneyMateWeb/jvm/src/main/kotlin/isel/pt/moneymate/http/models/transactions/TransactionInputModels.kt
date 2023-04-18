package isel.pt.moneymate.controller.models

import jakarta.validation.constraints.NotBlank

data class CreateTransactionDTO (
    @field:NotBlank(message = "Transaction ammount is required")
    val amount: Int,

    @field:NotBlank(message = "Transaction title is required")
    val title: String,

    val periodical: Int

){
    //fun toTransactionInputDTO() = TransactionDTO(amount, title, transactionType, periodical)
}

data class UpdateTransactionDTO (
    val categoryId: Int,
    val amount: Int,
    val title: String,
)