package pt.isel.moneymate.domain

import java.time.LocalDateTime


data class Transaction(
    val id : Int,
    val type: TransactionType,
    val description: String,
    val amount: Double,
    val category: Category,
    val createdAt: LocalDateTime,
    )

enum class TransactionType {
    EXPENSE,
    INCOME
}