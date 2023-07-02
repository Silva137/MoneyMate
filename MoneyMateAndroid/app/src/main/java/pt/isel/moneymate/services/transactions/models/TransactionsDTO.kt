package pt.isel.moneymate.services.transactions.models

data class TransactionsDTO(
    val transactions: List<TransactionDTO>,
    val totalCount: Int
)