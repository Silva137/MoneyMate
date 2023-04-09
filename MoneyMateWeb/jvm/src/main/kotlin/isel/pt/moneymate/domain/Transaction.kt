package isel.pt.moneymate.domain

import java.time.LocalDateTime

data class Transaction(
    val id: Int,
    val user : User,
    val amount: Long,
    val type: TransactionType,
    val category : Category,
    val description: String,
    val createdAt: LocalDateTime,
    //val periodicity: TransactionPeriodicity?
)

enum class TransactionType {
    INCOME,
    EXPENSE
}
