package pt.isel.moneymate.domain

import pt.isel.moneymate.services.users.models.UserDTO


data class Category(
    val id: Int,
    val name: String,
    val user: UserDTO
)