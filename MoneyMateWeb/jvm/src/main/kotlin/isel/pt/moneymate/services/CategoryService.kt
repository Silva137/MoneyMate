package isel.pt.moneymate.services

import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.http.models.categories.CategoriesDTO
import isel.pt.moneymate.http.models.categories.CategoryDTO
import isel.pt.moneymate.http.models.categories.CreateCategoryDTO
import isel.pt.moneymate.http.models.categories.UpdateCategoryDTO
import isel.pt.moneymate.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CategoryService(private val categoryRepository : CategoryRepository) {

    fun createCategory(categoryInput : CreateCategoryDTO, userId: Int): CategoryDTO {
        val categoryId = categoryRepository.createCategory(categoryInput.name, userId)
        val category = getCategoryById(categoryId)
        return CategoryDTO(category.name,category.user)
    }

    fun getCategories(offset: Int, limit: Int): CategoriesDTO {
        val categories = categoryRepository.getCategories(offset, limit) ?: throw NotFoundException("No categories found")
        val listDTO = categories.map { CategoryDTO(it.name, it.user?.toDTO())}
        return CategoriesDTO(listDTO)
    }

    fun getCategoryById(categoryId : Int) : CategoryDTO {
        val category = categoryRepository.getCategoryById(categoryId) ?: throw NotFoundException("Category with id $categoryId not found")
        return CategoryDTO(category.name, category.user?.toDTO())
    }

    fun updateCategory(categoryInput : UpdateCategoryDTO, categoryId: Int) : CategoryDTO {
        categoryRepository.updateCategoryName(categoryInput.name, categoryId)
        val updatedCategory = categoryRepository.getCategoryById(categoryId) ?: throw NotFoundException("Category with id $categoryId not found")
        return CategoryDTO(updatedCategory.name, updatedCategory.user?.toDTO())
    }

    fun deleteCategory(categoryId: Int) {
        categoryRepository.deleteCategoryById(categoryId)
    }
}