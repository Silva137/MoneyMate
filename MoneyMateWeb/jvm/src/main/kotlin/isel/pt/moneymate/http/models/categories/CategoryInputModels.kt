package isel.pt.moneymate.http.models.categories

import jakarta.validation.constraints.NotBlank

data class CreateCategoryDTO (
    @field:NotBlank(message = "Category name  is required")
    val name: String,
)

data class UpdateCategoryDTO (
    @field:NotBlank(message = "Category name  is required")
    val name: String,
)