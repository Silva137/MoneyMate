package isel.pt.moneymate.services.dtos

data class TransactionDTO(
    val amount: Int,
    val title: String,
    val transactionType: Int,
    val periodical: String
)