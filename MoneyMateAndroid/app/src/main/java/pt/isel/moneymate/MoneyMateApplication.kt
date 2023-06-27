package pt.isel.moneymate
import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager
import pt.isel.moneymate.session.SessionManagerSharedPrefs

interface DependenciesContainer {
    val moneymateService: MoneyMateService
    val jsonEncoder: Gson
    val sessionManager : SessionManager
}


class MoneyMateApplication : DependenciesContainer, Application() {

    override val jsonEncoder: Gson by lazy{ GsonBuilder().create()}

    override val sessionManager: SessionManager by lazy {SessionManagerSharedPrefs(context = this)}

    override val moneymateService: MoneyMateService by lazy { MoneyMateService(API_ENDPOINT, OkHttpClient(),jsonEncoder) }


    companion object{
        private const val API_ENDPOINT =
            "https://edd1-2001-8a0-7284-8e00-2dc9-f9a8-5ee4-deab.eu.ngrok.io"
        private const val TAG = "MoneyMateApp"
    }
}