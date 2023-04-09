package pt.isel.moneymate.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.moneymate.utils.BottomBar
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.domain.Transaction
import pt.isel.moneymate.domain.TransactionType
import pt.isel.moneymate.home.HomeScreen
import pt.isel.moneymate.profile.ProfileScreen
import pt.isel.moneymate.statistics.StatisticsScreen
import pt.isel.moneymate.transactions.BottomSheetContent
import pt.isel.moneymate.transactions.TransactionsScreen
import java.time.LocalDateTime

import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    var bottomState by remember { mutableStateOf("Home") }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val selectedTransaction = remember { mutableStateOf<Transaction?>(null) }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = { BottomSheetContent(selectedTransaction) },
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    ) {
        Scaffold(
            bottomBar = {
                BottomBar(
                    onHomeRequested = { bottomState = "Home" },
                    onTransactionsRequested = { bottomState = "Transactions" },
                    onStatisticsRequested = { bottomState = "Statistics" },
                    onProfileRequested = { bottomState = "Profile" }
                )
            },
        ) { _ ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) {
                    when (bottomState) {
                        "Home" -> HomeScreen()
                        "Transactions" -> TransactionsScreen(transactions, scope, bottomSheetState, selectedTransaction)
                        "Statistics" -> StatisticsScreen()
                        "Profile" -> ProfileScreen()
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}

val transactions = listOf(
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Lunch",
        category = Category("Food"),
        amount = 12.34,
        date = LocalDateTime.now()
    ),
    Transaction(
        type = TransactionType.INCOME,
        description = "Salary",
        category = Category("Work"),
        amount = 5678.9,
        date = LocalDateTime.now()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Movie ticket",
        category = Category("Entertainment"),
        amount = 9.99,
        date = LocalDateTime.now()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Lunch",
        category = Category("Food"),
        amount = 12.34,
        date = LocalDateTime.now()
    ),
    Transaction(
        type = TransactionType.INCOME,
        description = "Salary",
        category = Category("Work"),
        amount = 5678.9,
        date = LocalDateTime.now()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Movie ticket",
        category = Category("Entertainment"),
        amount = 9.99,
        date = LocalDateTime.now()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Lunch",
        category = Category("Food"),
        amount = 12.34,
        date = LocalDateTime.now()
    ),
    Transaction(
        type = TransactionType.INCOME,
        description = "Salary",
        category = Category("Work"),
        amount = 5678.9,
        date = LocalDateTime.now()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Movie ticket",
        category = Category("Entertainment"),
        amount = 9.99,
        date = LocalDateTime.now()
    )
)