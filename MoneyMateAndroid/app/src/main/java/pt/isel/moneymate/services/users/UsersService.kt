package pt.isel.moneymate.services.users

import com.google.gson.Gson
import isel.pt.moneymate.http.utils.Uris
import okhttp3.OkHttpClient

import pt.isel.moneymate.services.HTTPService
import pt.isel.moneymate.services.users.models.AuthenticationOutputModel
import pt.isel.moneymate.services.users.models.LoginInput
import pt.isel.moneymate.services.users.models.RegisterInput
import pt.isel.moneymate.services.users.models.getUserOutputModel
import pt.isel.moneymate.utils.send

class UsersService(
    apiEndpoint: String,
    private val httpClient: OkHttpClient,
    jsonEncoder: Gson
) : HTTPService(apiEndpoint,httpClient,jsonEncoder) {

    suspend  fun login(
        email: String,
        password: String
    ) : AuthenticationOutputModel {
        val request = postNoAuth(link = Uris.Authentication.LOGIN, body = LoginInput(email, password))
        val tokens = request.send(httpClient){ response ->
            handleResponse<AuthenticationOutputModel>(response, AuthenticationOutputModel::class.java)
        }
        return tokens
    }

    suspend fun register(
        username : String,
        password : String,
        email : String
    ) {
        val request = postNoAuth(link= Uris.Authentication.REGISTER,body = RegisterInput(username,email,password))
        request.send(httpClient) {}
    }

    suspend fun getUsername(token: String?): getUserOutputModel? {
        if (token == null) {
            return null
        }
        val request = get(link = Uris.Users.GET_USER, token = token)
        val user = request.send(httpClient) { response ->
            handleResponse<getUserOutputModel>(response, getUserOutputModel::class.java)
        }
        return user
    }
}