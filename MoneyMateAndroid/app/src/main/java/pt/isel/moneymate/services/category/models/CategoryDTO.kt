package pt.isel.moneymate.services.category.models

import pt.isel.moneymate.services.users.models.UserDTO

data class CategoryDTO(
    val id : Int,
    val name : String,
    val user : UserDTO
)