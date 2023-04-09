package isel.pt.moneymate.domain

data class Wallet(
    val id: Int,
    val name: String,
    val user: User,
    val transactions: MutableList<Transaction> = mutableListOf()
)