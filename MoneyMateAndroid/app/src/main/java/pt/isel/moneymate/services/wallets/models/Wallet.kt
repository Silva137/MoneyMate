package pt.isel.moneymate.services.wallets.models

import pt.isel.moneymate.domain.User

data class Wallet(
    val id: Int,
    val name: String,
    val user: User,
    val createdAt: String,
    val balance: Int
)