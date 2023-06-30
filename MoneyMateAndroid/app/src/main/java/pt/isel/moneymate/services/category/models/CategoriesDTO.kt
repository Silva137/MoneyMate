package pt.isel.moneymate.services.category.models

import pt.isel.moneymate.domain.Category

data class CategoriesDTO (
    val categories: List<Category>,
    val totalCount: Int
)

data class BothCategoriesDTO(
    val userCategories: CategoriesDTO,
    val systemCategories: CategoriesDTO
)
