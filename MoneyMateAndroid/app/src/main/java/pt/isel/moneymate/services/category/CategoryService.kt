package pt.isel.moneymate.services.category

import com.google.gson.Gson
import isel.pt.moneymate.http.utils.Uris
import okhttp3.OkHttpClient
import pt.isel.moneymate.services.HTTPService
import pt.isel.moneymate.services.category.models.BothCategoriesDTO
import pt.isel.moneymate.services.category.models.CreateCategory
import pt.isel.moneymate.utils.APIResult
import pt.isel.moneymate.utils.send

class CategoryService(
    apiEndpoint: String,
    private val httpClient: OkHttpClient,
    jsonEncoder: Gson
) : HTTPService(apiEndpoint, httpClient, jsonEncoder) {


    suspend fun getCategories(token: String?): APIResult<BothCategoriesDTO>? {
        if (token == null) {
            return null
        }
        val request = get(link = Uris.Category.GET_CATEGORIES, token = token)
        val categories = request.send(httpClient) { response ->
            handleResponse<BothCategoriesDTO>(response, BothCategoriesDTO::class.java)
        }
        return categories
    }

    suspend fun createCategory(token: String?, categoryName: String) :APIResult<Unit> {
        return try {
            if (token == null) {
                return APIResult.Error("No token available")
            }
            val request = post(
                link = Uris.Category.CREATE,
                token = token,
                body = CreateCategory(categoryName)
            )
            val response = request.send(httpClient) {}
            APIResult.Success(response)
        }catch(e: Exception){
            APIResult.Error(e.message ?: "An error occurred during create Transaction")
        }
    }

    suspend fun updateCategory(token: String?, categoryId: Int, updatedCategoryName: String): APIResult<Unit> {
        return try {
            if (token == null) {
                return APIResult.Error("No token available")
            }
            val request = patch(
                link = "/categories/${categoryId}",
                token = token,
                body = CreateCategory(updatedCategoryName)
            )
            val response = request.send(httpClient) {}
            APIResult.Success(response)
        } catch (e: Exception) {
            APIResult.Error(e.message ?: "An error occurred during update Category")
        }
    }

    suspend fun deleteCategory(token: String?, categoryId: Int): APIResult<Unit> {
        return try {
            if (token == null) {
                return APIResult.Error("No token available")
            }
            val request = delete(
                link = "/categories/$categoryId",
                token = token
            )
            val response = request.send(httpClient) {}
            APIResult.Success(response)
        } catch (e: Exception) {
            APIResult.Error(e.message ?: "An error occurred during delete Category")
        }
    }


}