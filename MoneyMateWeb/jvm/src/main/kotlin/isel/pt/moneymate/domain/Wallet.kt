package isel.pt.moneymate.domain

import java.sql.Date
import java.sql.Timestamp

data class Wallet(
    val id: Int,
    val name: String,
    val user: User,
    val date : Date
)