package isel.pt.moneymate.domain

import java.sql.Date
import java.time.LocalDateTime

data class RegularTransaction(
    val id: Int,
    val frquency: String,
    val transactionInfo: Transaction
)

