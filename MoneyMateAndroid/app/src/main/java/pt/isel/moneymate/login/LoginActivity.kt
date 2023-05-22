package pt.isel.moneymate.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.moneymate.DependenciesContainer
import pt.isel.moneymate.MoneyMateActivity
import pt.isel.moneymate.MoneyMateApplication
import pt.isel.moneymate.main.MainActivity
import pt.isel.moneymate.utils.viewModelInit

class LoginActivity: ComponentActivity() {

    companion object {
        fun navigate(origin: Activity) {
            with(origin) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: LoginViewModel by viewModels {
        viewModelInit {
            LoginViewModel(dependencies.moneymateService,dependencies.sessionManager)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            LoginScreen(
                state = viewModel.authenticationState,
                onLoginRequest = {username, password ->
                    viewModel.login(username = username, password = password)
                },
                onLoginSuccessful = {
                    MainActivity.navigate(origin = this)
                },

            )
        }
    }


}