package isel.pt.moneymate.services

import isel.pt.moneymate.domain.Category
import isel.pt.moneymate.repository.CategoryRepository
import isel.pt.moneymate.services.dtos.CategoryDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class CategoryService(private val categoryRepository : CategoryRepository) {

    fun createCategory(categoryInput : CategoryDTO): Int {
        return categoryRepository.createCategory(
            categoryInput.name,
            1
        )
    }

    fun getCategories(): List<Category> {
        return categoryRepository.getCategories()
    }

    fun getCategoryById(categoryId : Int) : Category? {
        return categoryRepository.getCategoryById(categoryId)
    }

    fun updateCategory(categoryInput : CategoryDTO, categoryId: Int) : Category? {
        categoryRepository.updateCategoryName(
            categoryInput.name,
            categoryId
        )
        return getCategoryById(categoryId)
    }

    fun deleteCategory(categoryId: Int){
        categoryRepository.deleteCategoryById(categoryId)
    }
}