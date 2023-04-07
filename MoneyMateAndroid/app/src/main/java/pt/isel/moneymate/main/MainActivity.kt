package pt.isel.moneymate.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import pt.isel.moneymate.home.HomeActivity
import pt.isel.moneymate.profile.ProfileActivity
import pt.isel.moneymate.statistics.StatisticsActivity
import pt.isel.moneymate.theme.MoneyMateTheme
import pt.isel.moneymate.transactions.TransactionsActivity

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    companion object {
        fun navigate(context: Context) {
            with(context) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyMateTheme {
                MainScreen()
            }
        }
    }
}
