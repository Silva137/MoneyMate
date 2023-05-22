package pt.isel.moneymate.services.users

import com.google.gson.Gson
import okhttp3.OkHttpClient

import pt.isel.moneymate.services.HTTPService
import pt.isel.moneymate.services.users.models.AuthenticationOutputModel
import pt.isel.moneymate.services.users.models.LoginInput
import pt.isel.moneymate.utils.Uris
import pt.isel.moneymate.utils.send

class UsersService(
    apiEndpoint: String,
    private val httpClient: OkHttpClient,
    jsonEncoder: Gson
) : HTTPService(apiEndpoint,httpClient,jsonEncoder) {

    suspend  fun login(
        username: String,
        password: String
    ) : AuthenticationOutputModel {
        val request = postNoAuth(link = Uris.Authentication.LOGIN, body = LoginInput(username, password))
        val tokens = request.send(httpClient){ response ->
            handleResponse<AuthenticationOutputModel>(response, AuthenticationOutputModel::class.java)
        }
        return tokens
    }
}