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

data class CategoriesBalanceDTO(
    val balanceList: List<CategoryBalanceDTO>,
    val totalCount: Int = balanceList.size
)

data class CategoryBalanceDTO(
    val category: CategoryDTO,
    val balance: Int
)

data class PosAndNegCategoryBalanceDTO(
    val neg: CategoriesBalanceDTO,
    val pos: CategoriesBalanceDTO,
)
