package pt.isel.moneymate.services.transactions

import com.google.gson.Gson
import isel.pt.moneymate.http.utils.Uris
import okhttp3.OkHttpClient
import pt.isel.moneymate.services.HTTPService
import pt.isel.moneymate.services.category.models.CategoriesBalanceDTO
import pt.isel.moneymate.services.category.models.CreateCategory
import pt.isel.moneymate.services.category.models.PosAndNegCategoryBalanceDTO
import pt.isel.moneymate.services.transactions.models.CreateTransaction
import pt.isel.moneymate.services.transactions.models.TransactionsDTO
import pt.isel.moneymate.utils.APIResult
import pt.isel.moneymate.utils.send

class TransactionService(
    apiEndpoint: String,
    private val httpClient: OkHttpClient,
    jsonEncoder: Gson
) : HTTPService(apiEndpoint,httpClient,jsonEncoder) {


    suspend fun createTransaction(token: String?, categoryId: Int, walletId: Int, amount: Float, title: String) {
        if (token == null) {
            return
        }
        val request = post(link = "/transactions/wallets/$walletId/categories/$categoryId", token = token, body = CreateTransaction(amount, title))
        request.send(httpClient){}
    }

    suspend fun getWalletTransactions(
        token: String?,
        walletId: Int,
        startDate: String,
        endDate: String,
        sortedBy: String,
        orderBy: String
    ): APIResult<TransactionsDTO>? {
        if (token == null) {
            return null
        }

        val request = get(
            link = Uris.Transactions.GET_ALL + "$walletId?sortedBy=$sortedBy&orderBy=$orderBy&startDate=$startDate&endDate=$endDate&limit&offset",
            token
        )
        val transactions = request.send(httpClient) { response ->
            handleResponse<TransactionsDTO>(response, TransactionsDTO::class.java)
        }
        return transactions
    }

    suspend fun getCategoryBalance(token: String?, walletId: Int,startDate: String, endDate: String): PosAndNegCategoryBalanceDTO? {
        if (token == null) {
            return null
        }
        val request = get(link = "/transactions/wallets/$walletId/categories/posneg/balance?startDate=$startDate&endDate=$endDate", token = token)
        val categoriesBalance = request.send(httpClient) { response ->
            handleResponse<PosAndNegCategoryBalanceDTO>(response, PosAndNegCategoryBalanceDTO::class.java)
        }
        return categoriesBalance
    }
}