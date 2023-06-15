package pt.isel.moneymate.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.moneymate.DependenciesContainer
import pt.isel.moneymate.login.LoginViewModel
import pt.isel.moneymate.profile.ProfileActivity
import pt.isel.moneymate.profile.ProfileState
import pt.isel.moneymate.statistics.StatisticsActivity
import pt.isel.moneymate.theme.MoneyMateTheme
import pt.isel.moneymate.transactions.TransactionsActivity
import pt.isel.moneymate.utils.viewModelInit

class HomeActivity : ComponentActivity() {
    companion object {
        fun navigate(context: Context) {
            with(context) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: HomeViewModel by viewModels {
        viewModelInit {
            HomeViewModel(dependencies.moneymateService,dependencies.sessionManager)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (viewModel.state == HomeViewModel.WalletState.IDLE) {
            viewModel.fetchWallets()
        }


        setContent {


            HomeScreen(
                wallets = viewModel.wallets,
                selectedWalletId = viewModel.selectedWalletId,
                onWalletSelected = { walletId ->
                    // Update the selected wallet ID
                    viewModel.selectedWalletId = walletId
                },
                onProfileClick = {
                    ProfileActivity.navigate(this)
                    finish()
                },
                onTransactionClick = {
                    Log.v("ID","${viewModel.selectedWalletId}")
                    TransactionsActivity.navigate(this,viewModel.selectedWalletId)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {

}