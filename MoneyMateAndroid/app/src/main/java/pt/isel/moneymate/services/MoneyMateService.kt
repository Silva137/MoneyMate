package pt.isel.moneymate.services

import com.google.gson.Gson
import okhttp3.OkHttpClient

import pt.isel.moneymate.services.users.UsersService

class MoneyMateService(
    apiEndpoint: String,
    httpClient: OkHttpClient,
    jsonEncoder: Gson
) {

    val usersService = UsersService(apiEndpoint, httpClient, jsonEncoder)
    /*

    val gamesService = GamesService(apiSEndpoint, httpClient, jsonEncoder)
    val playersService = PlayersService(apiEndpoint, httpClient, jsonEncoder)


    suspend fun getHome(): APIResult<GetHomeOutput> = get(link = HOME_LINK)

    companion object {
        private const val HOME_LINK = "/"
    }

     */
}
