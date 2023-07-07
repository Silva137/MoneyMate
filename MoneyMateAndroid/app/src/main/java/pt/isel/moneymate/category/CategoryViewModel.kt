package pt.isel.moneymate.category

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.home.HomeViewModel
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager
import pt.isel.moneymate.utils.APIResult

class CategoryViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var _state: CategoryState by mutableStateOf(CategoryState.IDLE)
    val state: CategoryState get() = _state

    private var _categories: List<Category> by mutableStateOf(emptyList())
    val categories: List<Category> get() = _categories

    private var _errorMessage by mutableStateOf<String?>(null)
    val errorMessage: String?
        get() = _errorMessage

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val categoriesResponse = moneymateService.categoriesService.getCategories(token)

                when (categoriesResponse) {
                    is APIResult.Success -> {
                        val bothCategoriesDTO = categoriesResponse.data

                        _categories = if (bothCategoriesDTO != null) {
                            val userCategories = bothCategoriesDTO.userCategories.categories
                            val systemCategories = bothCategoriesDTO.systemCategories.categories

                            val combinedCategories = userCategories + systemCategories
                            combinedCategories
                        } else {
                            emptyList()
                        }
                        Log.v("CATEGORIES", _categories.toString())
                    }
                    is APIResult.Error -> {
                        val errorMessage = categoriesResponse.message
                        _errorMessage = errorMessage
                        _state = CategoryState.ERROR
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to fetch categories", e)
            }
        }
    }

    fun createCategory(name : String){
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val response = moneymateService.categoriesService.createCategory(token,name)
                when (response){
                    is APIResult.Success ->
                        fetchCategories()

                    is APIResult.Error -> {
                        val errorMessage = response.message
                        _errorMessage = errorMessage
                        _state = CategoryState.ERROR
                    }
                }
            }catch (e: Exception){
                Log.e("ERROR", "Failed to create category", e)
            }
        }
    }

    fun updateCategory(categoryId: String, updatedCategoryName: String) {
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val response = moneymateService.categoriesService.updateCategory(token, categoryId, updatedCategoryName)
                when (response) {
                    is APIResult.Success ->
                        fetchCategories()

                    is APIResult.Error -> {
                        val errorMessage = response.message
                        _errorMessage = errorMessage
                        _state = CategoryState.ERROR
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to update category", e)
            }
        }
    }

    suspend fun deleteCategory(categoryId: String) {
        viewModelScope.launch {
            try {
                val token = sessionManager.accessToken
                val response = moneymateService.categoriesService.deleteCategory(token, categoryId)
                when (response) {
                    is APIResult.Success ->
                        fetchCategories()

                    is APIResult.Error -> {
                        val errorMessage = response.message
                        _errorMessage = errorMessage
                        _state = CategoryState.ERROR
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Failed to delete category", e)
            }
        }
    }






    enum class CategoryState {
        IDLE,
        GETTING_CATEGORIES,
        FINISHED,
        ERROR
    }
}