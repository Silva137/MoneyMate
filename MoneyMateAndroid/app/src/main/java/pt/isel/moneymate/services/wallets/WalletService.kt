package pt.isel.moneymate.services.wallets

import android.util.Log
import com.google.gson.Gson
import isel.pt.moneymate.http.utils.Uris
import okhttp3.OkHttpClient
import pt.isel.moneymate.services.HTTPService
import pt.isel.moneymate.services.transactions.models.WalletBalanceDTO
import pt.isel.moneymate.services.wallets.models.CreateWallet
import pt.isel.moneymate.services.wallets.models.WalletDTO
import pt.isel.moneymate.services.wallets.models.getWalletResponse
import pt.isel.moneymate.utils.send
import java.time.LocalDate

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

    suspend fun getWalletBalance(token: String?, walletId: Int, startDate: LocalDate, endDate: LocalDate) : WalletBalanceDTO? {
        if (token == null) {
            return null
        }
        val request = get(link = "/transactions/wallets/${walletId}/balance?startDate=${startDate}&endDate=${endDate}",token)
        val walletBalance = request.send(httpClient){ response ->
            handleResponse<WalletBalanceDTO>(response,WalletBalanceDTO::class.java)
        }
        return walletBalance
    }
}