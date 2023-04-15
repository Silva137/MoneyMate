package isel.pt.moneymate.controller.models

import isel.pt.moneymate.services.dtos.CategoryDTO
import jakarta.validation.constraints.NotBlank

data class CategoryInputModel (
    @field:NotBlank(message = "Category name  is required")
    val name: String,
){
    fun toCategoryInputDTO() = CategoryDTO(name)
}