package pt.isel.moneymate.services.category

import com.google.gson.Gson
import isel.pt.moneymate.http.utils.Uris
import okhttp3.OkHttpClient
import pt.isel.moneymate.services.HTTPService
import pt.isel.moneymate.services.category.models.BothCategoriesDTO
import pt.isel.moneymate.services.category.models.CreateCategory
import pt.isel.moneymate.utils.send

class CategoryService(
    apiEndpoint: String,
    private val httpClient: OkHttpClient,
    jsonEncoder: Gson
) : HTTPService(apiEndpoint, httpClient, jsonEncoder) {


    suspend fun getCategories(token: String?): BothCategoriesDTO? {
        if (token == null) {
            return null
        }
        val request = get(link = Uris.Category.GET_CATEGORIES, token = token)
        val categories = request.send(httpClient) { response ->
            handleResponse<BothCategoriesDTO>(response, BothCategoriesDTO::class.java)
        }
        return categories
    }

    suspend fun createCategory(token: String?, categoryName: String) {
        if (token == null) {
            return
        }
        val request = post(link = Uris.Category.CREATE, token = token, body = CreateCategory(categoryName))
        request.send(httpClient){}
    }
}