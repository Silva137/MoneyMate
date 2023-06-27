package pt.isel.moneymate.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.moneymate.DependenciesContainer
import pt.isel.moneymate.home.HomeViewModel
import pt.isel.moneymate.profile.ProfileViewModel
import pt.isel.moneymate.statistics.StatisticsViewModel
import pt.isel.moneymate.theme.MoneyMateTheme
import pt.isel.moneymate.transactions.TransactionsViewModel
import pt.isel.moneymate.utils.viewModelInit

class MainActivity : ComponentActivity() {
    companion object {
        fun navigate(origin: Activity) {
            with(origin) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val dependencies by lazy { application as DependenciesContainer }

    private val homeViewModel: HomeViewModel by viewModels {
        viewModelInit { HomeViewModel(dependencies.moneymateService,dependencies.sessionManager) }
    }

    private val transactionsViewModel: TransactionsViewModel by viewModels {
        viewModelInit { TransactionsViewModel(dependencies.moneymateService, dependencies.sessionManager) }
    }

    private val statisticsViewModel: StatisticsViewModel by viewModels {
        viewModelInit { StatisticsViewModel(dependencies.moneymateService, dependencies.sessionManager) }
    }

    private val profileViewModel: ProfileViewModel by viewModels {
        viewModelInit { ProfileViewModel(dependencies.moneymateService, dependencies.sessionManager) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyMateTheme {
                MainScreen(
                    homeViewModel = homeViewModel,
                    transactionsViewModel = transactionsViewModel,
                    statisticsViewModel = statisticsViewModel,
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}