package pt.isel.moneymate.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.moneymate.DependenciesContainer
import pt.isel.moneymate.login.LoginActivity
import pt.isel.moneymate.utils.viewModelInit

class RegisterActivity : ComponentActivity() {

    companion object {
        fun navigate(origin: Activity) {
            with(origin) {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: RegisterViewModel by viewModels {
        viewModelInit {
            RegisterViewModel(dependencies.moneymateService)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegisterScreen(
                state = viewModel.authenticationState,
                onSignUpRequest = { username, password, email ->
                    viewModel.register(username = username, password = password, email = email)
                },
                onSignUpSuccessful = {
                    LoginActivity.navigate(origin = this)
                    finish()
                },
                onLoginRequest = {
                    LoginActivity.navigate(origin = this)
                    finish()
            }

                )
        }
    }


}