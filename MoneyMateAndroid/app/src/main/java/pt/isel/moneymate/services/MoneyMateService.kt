package pt.isel.moneymate.services

import com.google.gson.Gson
import okhttp3.OkHttpClient
import pt.isel.moneymate.services.category.CategoryService
import pt.isel.moneymate.services.transactions.TransactionService

import pt.isel.moneymate.services.users.UsersService
import pt.isel.moneymate.services.wallets.WalletService

class MoneyMateService(
    apiEndpoint: String,
    httpClient: OkHttpClient,
    jsonEncoder: Gson
) {
    val usersService = UsersService(apiEndpoint, httpClient, jsonEncoder)
    val walletsService = WalletService(apiEndpoint,httpClient,jsonEncoder)
    val transactionsService = TransactionService(apiEndpoint,httpClient,jsonEncoder)
    val categoriesService = CategoryService(apiEndpoint,httpClient,jsonEncoder)
}
