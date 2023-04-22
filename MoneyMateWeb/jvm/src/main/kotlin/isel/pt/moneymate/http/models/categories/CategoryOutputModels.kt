package isel.pt.moneymate.http.models.categories

import isel.pt.moneymate.http.models.users.UserDTO
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank

data class CategoryDTO (
    val id: Int,
    @field:NotBlank(message = "Category name is required")
    val name: String,
    @field:Nullable
    val user: UserDTO?
)

data class CategoriesDTO (
    val categories: List<CategoryDTO>,
    @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
    val totalCount: Int = categories.size
)

