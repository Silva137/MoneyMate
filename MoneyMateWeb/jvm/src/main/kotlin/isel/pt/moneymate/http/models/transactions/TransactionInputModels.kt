package isel.pt.moneymate.controller.models

import jakarta.validation.constraints.NotBlank

data class CreateTransactionDTO (
    @field:NotBlank(message = "Transaction ammount is required")
    val amount: Int,

    @field:NotBlank(message = "Transaction title is required")
    val title: String,

    // TODO Int 0 representa - e 1 representa +
    @field:NotBlank(message = "Transaction type is required")
    val transactionType: Int,

    val periodical: String

){
    //fun toTransactionInputDTO() = TransactionDTO(amount, title, transactionType, periodical)
}

data class UpdateTransactionDTO (
    val categoryId: Int,
    val amount: Int,
    val title: String,
    val transactionType: Int,
)