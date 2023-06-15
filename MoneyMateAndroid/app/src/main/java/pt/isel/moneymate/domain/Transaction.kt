package pt.isel.moneymate.domain

import java.time.LocalDateTime


data class Transaction(
    val type: TransactionType,
    val description: String,
    val amount: Double,
    val category: Category
)

enum class TransactionType {
    EXPENSE,
    INCOME
}