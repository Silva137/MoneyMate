package pt.isel.moneymate
import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager
import pt.isel.moneymate.session.SessionManagerSharedPrefs
import pt.isel.moneymate.utils.AuthInterceptor

interface DependenciesContainer {
    val moneymateService: MoneyMateService
    val jsonEncoder: Gson
    val sessionManager : SessionManager
}


class MoneyMateApplication : DependenciesContainer, Application() {

    override val jsonEncoder: Gson by lazy{ GsonBuilder().create()}

    override val sessionManager: SessionManager by lazy {SessionManagerSharedPrefs(context = this)}

    override val moneymateService: MoneyMateService by lazy {
        val interceptor = AuthInterceptor(sessionManager, API_ENDPOINT)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        MoneyMateService(API_ENDPOINT, okHttpClient, jsonEncoder)
    }


    companion object{
        private const val API_ENDPOINT = "https://aa36-2001-8a0-7284-8e00-9067-49ed-f3aa-ece0.ngrok-free.app"
        private const val TAG = "MoneyMateApp"
    }
}