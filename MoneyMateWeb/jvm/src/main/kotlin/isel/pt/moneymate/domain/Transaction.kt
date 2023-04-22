package isel.pt.moneymate.domain

import java.sql.Date

data class Transaction(
    val id: Int,
    val title: String,
    val amount: Int,
    val user: User,
    val wallet: Wallet,
    val category: Category,
    val createdAt: Date,
    val periodical: Int
)

