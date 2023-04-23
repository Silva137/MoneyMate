package isel.pt.moneymate.domain

import isel.pt.moneymate.http.models.categories.CategoryDTO

data class Category(
    val id: Int,
    val name: String,
    val user: User?
){
    fun toDTO() = CategoryDTO(
        id,
        name,
        user?.toDTO()
    )
}


