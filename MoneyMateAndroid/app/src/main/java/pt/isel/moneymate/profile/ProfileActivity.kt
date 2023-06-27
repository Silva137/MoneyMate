package pt.isel.moneymate.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.moneymate.DependenciesContainer
import pt.isel.moneymate.home.HomeActivity
import pt.isel.moneymate.utils.viewModelInit

class ProfileActivity: ComponentActivity() {
    companion object {
        fun navigate(context: Context) {
            with(context) {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: ProfileViewModel by viewModels {
        viewModelInit {
            ProfileViewModel(dependencies.moneymateService, dependencies.sessionManager)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (viewModel.state == ProfileState.IDLE) {
            viewModel.getUsername()
        }


        setContent {
            ProfileScreen(viewModel.username)
        }
    }

    override fun onBackPressed() {
        HomeActivity.navigate(this)
        finish()
    }

}