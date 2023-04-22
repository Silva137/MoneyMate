package isel.pt.moneymate.domain

import java.sql.Date

data class Wallet(
    val id: Int,
    val name: String,
    val user: User,
    val createdAt : Date
)