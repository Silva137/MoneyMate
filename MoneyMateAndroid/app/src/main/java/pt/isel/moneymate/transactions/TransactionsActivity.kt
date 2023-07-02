package pt.isel.moneymate.transactions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import pt.isel.moneymate.DependenciesContainer
import pt.isel.moneymate.utils.viewModelInit

class TransactionsActivity: ComponentActivity() {
    companion object {
        fun navigate(context: Context, walletId : Int) {
            with(context) {
                val intent = Intent(this, TransactionsActivity::class.java)
                intent.putExtra("selectedWalletId", walletId)
                startActivity(intent)
            }
        }
    }

    private val dependencies by lazy { application as DependenciesContainer }

    private val viewModel: TransactionsViewModel by viewModels {
        viewModelInit {
            TransactionsViewModel(dependencies.moneymateService, dependencies.sessionManager)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("TA","${intent.getIntExtra("selectedWalletId",0)}")
        val walletId = intent.getIntExtra("selectedWalletId", 0)
        setContent {
            TransactionsScreen(
                transactions = viewModel.transactions,
                onSearchClick = {startTime, endTime ->
                    viewModel.fetchTransactions(walletId,startTime,endTime,"bydate", "ASC")
                }
            )
        }
    }
}