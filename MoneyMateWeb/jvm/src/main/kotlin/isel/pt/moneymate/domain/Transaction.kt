package isel.pt.moneymate.domain

import java.sql.Date
import java.time.LocalDateTime

data class Transaction(
    val id: Int,
    val title: String,
    val amount: Float,
    val user: User,
    val wallet: Wallet,
    val category: Category,
    val createdAt: LocalDateTime,
    val periodical: Int
)

