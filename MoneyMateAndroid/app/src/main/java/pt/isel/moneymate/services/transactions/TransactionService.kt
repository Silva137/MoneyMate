package pt.isel.moneymate.services.transactions

import com.google.gson.Gson
import isel.pt.moneymate.http.utils.Uris
import okhttp3.OkHttpClient
import pt.isel.moneymate.services.HTTPService
import pt.isel.moneymate.services.transactions.models.TransactionsDTO
import pt.isel.moneymate.utils.send

class TransactionService(
    apiEndpoint: String,
    private val httpClient: OkHttpClient,
    jsonEncoder: Gson
) : HTTPService(apiEndpoint,httpClient,jsonEncoder) {


    suspend fun getWalletTransactions(walletId : Int, token : String?, startDate : String, endDate : String) : TransactionsDTO? {
        if(token == null) {
            return null
        }

        val request = get(link = Uris.Transactions.GET_ALL + "${walletId}?sortedBy=byprice&orderBy=ASC&startDate=${startDate}&endDate=${endDate}&limit&offset",token)
        val transactions = request.send(httpClient){ response ->
            handleResponse<TransactionsDTO>(response,TransactionsDTO::class.java)
        }
        return transactions
    }



}