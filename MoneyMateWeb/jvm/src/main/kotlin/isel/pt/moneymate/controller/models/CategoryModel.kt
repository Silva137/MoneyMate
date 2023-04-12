package isel.pt.moneymate.controller.models

import isel.pt.moneymate.services.dtos.CreateCategoryDTO
import isel.pt.moneymate.services.dtos.CreateWalletDTO
import jakarta.validation.constraints.NotBlank

data class CategoryInputModel (
    @field:NotBlank(message = "Category name  is required")
    val name: String,
){
    fun toCategoryInputDTO() = CreateCategoryDTO(name)
}