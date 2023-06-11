package isel.pt.moneymate.services

import isel.pt.moneymate.domain.Category
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.exceptions.ForbiddenException
import isel.pt.moneymate.exceptions.InvalidParameterException
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.exceptions.UnauthorizedException
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
    private val transactionRepository: TransactionRepository,
){
    var systemCategories: List<Category>? = null

    fun createCategory(categoryInput: CreateCategoryDTO, userId: Int): CategoryDTO {
        val categoryId = categoryRepository.createCategory(categoryInput.name, userId)
        return getCategoryById(categoryId)
    }

    fun getCategoriesGivenUser(userId: Int, offset: Int, limit: Int): CategoriesDTO {
        val categories = categoryRepository.getCategories(userId, offset, limit)
            ?: throw NotFoundException("No categories found")
        return categories.toDTO()
    }

    fun getSystemCategories(offset: Int, limit: Int): CategoriesDTO {
       getSystemCategories()
        if (offset >= systemCategories!!.size)
            throw InvalidParameterException("Offset value exceeds the number of system categories")

        val endIndex = minOf(offset + limit, systemCategories!!.size)
        return systemCategories!!.subList(offset,endIndex).toDTO()
    }

    fun getCategoryById(user:User, categoryId : Int) : CategoryDTO {
        verifyUserOnCategory(user.id, categoryId)
        return getCategoryById(categoryId)
    }

    fun updateCategory(user: User, categoryInput : UpdateCategoryDTO, categoryId: Int) : CategoryDTO {
        isSystemCategory(categoryId)
        verifyUserOnCategory(user.id, categoryId)

        categoryRepository.updateCategoryName(categoryInput.name, categoryId)
        return getCategoryById(categoryId)
    }

    fun deleteCategory(user: User, categoryId: Int) {
        isSystemCategory(categoryId)
        verifyUserOnCategory(user.id, categoryId)

        transactionRepository.updateTransactionsCategories(user.id, categoryId, 0)
        categoryRepository.deleteCategoryById(categoryId)
    }

    /**
     * Auxiliar
     */
    fun getCategoryById(categoryId : Int) : CategoryDTO {
        val category = categoryRepository.getCategoryById(categoryId)
            ?: throw NotFoundException("Category with id $categoryId not found")
        return category.toDTO()
    }

    fun getSystemCategories(){
        if (systemCategories.isNullOrEmpty())
            systemCategories = categoryRepository.getSystemCategories()
        if (systemCategories == null)
            throw NotFoundException("System categories not found")
    }

    fun isSystemCategory(categoryId: Int){
        // Cant delete a base category, (that belongs to system User)
        val categoryDTO = getCategoryById(categoryId)
        if(categoryDTO.user.id == SYSTEM_USER)
            throw ForbiddenException("Cant update or delete a system category, id: $categoryId")
    }

    fun verifyUserOnCategory(userId: Int, categoryId: Int){
        val userOfCategory = categoryRepository.getUserOfCategory(categoryId)
            ?: throw NotFoundException("Category with id $categoryId not found")

        if(userOfCategory != userId)
            throw UnauthorizedException("User does not have permission to perform this action on Category $categoryId")
    }
}