package isel.pt.moneymate.services

import isel.pt.moneymate.controller.models.UpdateTransactionDTO
import isel.pt.moneymate.exceptions.ForbiddenException
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.http.models.categories.*
import isel.pt.moneymate.repository.CategoryRepository
import isel.pt.moneymate.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

const val DEFAULT_CATEGORY = 0
const val SYSTEM_USER = 0

@Service
@Transactional(rollbackFor = [Exception::class])
class CategoryService(
    private val categoryRepository : CategoryRepository,
    private val transactionService: TransactionService
){

    fun createCategory(categoryInput: CreateCategoryDTO, userId: Int): CategoryDTO {
        val categoryId = categoryRepository.createCategory(categoryInput.name, userId)
        return getCategoryById(categoryId)
    }

    fun getCategories(offset: Int, limit: Int): CategoriesDTO {
        val categories = categoryRepository.getCategories(offset, limit)
            ?: throw NotFoundException("No categories found")
        return categories.toDTO()
    }

    fun getCategoryById(categoryId : Int) : CategoryDTO {
        val category = categoryRepository.getCategoryById(categoryId)
            ?: throw NotFoundException("Category with id $categoryId not found")
        return category.toDTO()
    }

    fun updateCategory(categoryInput : UpdateCategoryDTO, categoryId: Int) : CategoryDTO {
        categoryRepository.updateCategoryName(categoryInput.name, categoryId)
        return getCategoryById(categoryId)
    }

    fun deleteCategory(categoryId: Int, userId: Int) {
        // Cant delete category called "Outros"
        if(categoryId == DEFAULT_CATEGORY)
            throw ForbiddenException("Category with id = 0 cannot be deleted")
        val categoryDTO = getCategoryById(categoryId)

        // Cant delete a base category, (that belongs to system User)
        if(categoryDTO.user.id == SYSTEM_USER)
            throw ForbiddenException("Category with id $categoryId cannot be deleted")

        //If can delete changeTransactions To System Category "Outros"
        transactionService.updateTransactionsCategories(userId, categoryId, DEFAULT_CATEGORY)
        categoryRepository.deleteCategoryById(categoryId)
    }
}