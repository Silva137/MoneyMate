package isel.pt.moneymate.domain

import java.sql.Date

data class Transaction(
    val id: Int,
    val user: User,
    val amount: Int,
    val type: Int,
    val category: Category,
    val title: String,
    val createdAt: Date,
    //val periodicity: TransactionPeriodicity?
)

enum class TransactionType {
    INCOME,
    EXPENSE
}
