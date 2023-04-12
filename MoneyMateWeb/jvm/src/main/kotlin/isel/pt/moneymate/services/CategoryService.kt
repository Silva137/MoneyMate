package isel.pt.moneymate.services

import isel.pt.moneymate.domain.Category
import isel.pt.moneymate.repository.CategoryRepository
import isel.pt.moneymate.services.dtos.CreateCategoryDTO
import isel.pt.moneymate.utils.Uris
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CategoryService(private val categoryRepository : CategoryRepository) {

    fun createCategory(categoryInput : CreateCategoryDTO): Int {
        return categoryRepository.createCategory(name = categoryInput.name, userId = 1)
    }

    fun getCategories(): List<Category> {
        return categoryRepository.getCategories()
    }

    fun getCategoryById(categoryId : Int) : Category? {
        return categoryRepository.getCategoryById(categoryId = categoryId)
    }

    fun updateCategory(categoryInput : CreateCategoryDTO, categoryId: Int) : Category? {
        categoryRepository.updateCategoryName(newName = categoryInput.name, categoryId = categoryId)
        return getCategoryById(categoryId = categoryId)
    }

    fun deleteCategory(categoryId: Int){
        categoryRepository.deleteCategoryById(categoryId = categoryId)
    }
}