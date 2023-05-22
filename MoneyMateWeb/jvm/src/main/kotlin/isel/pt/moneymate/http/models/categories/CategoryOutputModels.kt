package isel.pt.moneymate.http.models.categories

import isel.pt.moneymate.domain.Category
import isel.pt.moneymate.http.models.users.UserDTO
import isel.pt.moneymate.http.models.users.toDTO
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank

data class CategoryDTO (
    val id: Int,
    @field:NotBlank(message = "Category name is required")
    val name: String,
    @field:Nullable
    val user: UserDTO
){
    constructor(category: Category): this(
        category.id,
        category.name,
        category.user.toDTO()
    )
}
fun Category.toDTO() = CategoryDTO(this)


data class CategoriesDTO (
    val categories: List<CategoryDTO>,
    @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
    val totalCount: Int = categories.size
)

fun List<Category>.toDTO(): CategoriesDTO{
    val categories = this.map { it.toDTO() }
    return CategoriesDTO(categories)
}

