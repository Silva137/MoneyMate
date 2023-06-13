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
            "https://f8e6-95-136-89-178.ngrok-free.app"
        private const val TAG = "MoneyMateApp"
    }

}