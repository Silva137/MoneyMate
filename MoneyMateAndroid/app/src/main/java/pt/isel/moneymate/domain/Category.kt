package pt.isel.moneymate.domain


data class Category(
    val id: Int,
    val name: String,
    val user: User
)