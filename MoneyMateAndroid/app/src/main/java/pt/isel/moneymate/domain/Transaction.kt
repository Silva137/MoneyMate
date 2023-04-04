package pt.isel.moneymate.domain

import java.util.*


data class Transaction(
    val type: TransactionType,
    val description: String,
    val amount: Double,
    val date: Date,
    val category: Category
)

enum class TransactionType {
    EXPENSE,
    INCOME
}