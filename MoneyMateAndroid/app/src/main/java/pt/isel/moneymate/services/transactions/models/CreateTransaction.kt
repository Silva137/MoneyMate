package pt.isel.moneymate.services.transactions.models

data class CreateTransaction(
    val amount: Float,
    val title: String,
    //val periodical: Int
)