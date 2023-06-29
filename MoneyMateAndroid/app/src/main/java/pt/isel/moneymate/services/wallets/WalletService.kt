package pt.isel.moneymate.services.wallets

import com.google.gson.Gson
import isel.pt.moneymate.http.utils.Uris
import okhttp3.OkHttpClient
import pt.isel.moneymate.services.HTTPService
import pt.isel.moneymate.services.wallets.models.CreateWallet
import pt.isel.moneymate.services.wallets.models.getWalletResponse
import pt.isel.moneymate.utils.send

class WalletService(
    apiEndpoint: String,
    private val httpClient: OkHttpClient,
    jsonEncoder: Gson
) : HTTPService(apiEndpoint, httpClient, jsonEncoder) {


    suspend fun getWallets(token: String?): getWalletResponse? {
        if (token == null) {
            return null
        }
        val request = get(link = Uris.Wallets.GET_WALLETS_OF_USER, token = token)
        val wallets = request.send(httpClient) { response ->
            handleResponse<getWalletResponse>(response, getWalletResponse::class.java)
        }
        return wallets
    }

    suspend fun createWallet(token: String?, walletName: String) {
        if (token == null) {
           return
        }
        val request = post(link = Uris.Wallets.CREATE, token = token, body = CreateWallet(walletName))
        request.send(httpClient){}
    }
}