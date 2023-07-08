package pt.isel.moneymate.services.transactions.models

data class UpdateTransaction(
    val categoryId: Int,
    val amount: Float,
    val title: String
)
