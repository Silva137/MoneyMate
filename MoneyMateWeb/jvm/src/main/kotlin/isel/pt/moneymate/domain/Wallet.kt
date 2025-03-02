package isel.pt.moneymate.domain

import isel.pt.moneymate.http.models.categories.CategoryDTO
import isel.pt.moneymate.http.models.wallets.WalletDTO
import java.sql.Date

data class Wallet(
    val id: Int,
    val name: String,
    val user: User,
    val createdAt : Date
)
