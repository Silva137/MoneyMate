package pt.isel.moneymate.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.moneymate.DependenciesContainer
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.domain.User
import pt.isel.moneymate.services.transactions.models.WalletBalanceDTO
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
                categories = listOf(
                    Category(1, "Saude", User(1,"silva","silva")),
                    Category(2, "Desporto", User(1,"silva","silva")),
                    Category(3, "Carro", User(1,"silva","silva"))
                ),
                selectedWalletId = viewModel.selectedWalletId,
                onWalletSelected = { walletId ->
                    viewModel.selectedWalletId = walletId
                },
                walletBalance = WalletBalanceDTO(22.00, 1300.0),
                onCategoriesDropdownClick = {viewModel.fetchCategories()},
                onCreateTransactionButtonClick = {_,_,_->}
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {

}