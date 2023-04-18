package isel.pt.moneymate.domain

import java.sql.Date

data class Transaction(
    val id: Int,
    val user: User,
    val wallet: Wallet,
    val category: Category,
    val amount: Int,
    val dateOfCreation: Date,
    val title: String,
    val periodical: Int
)

