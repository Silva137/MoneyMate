package pt.isel.moneymate.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import pt.isel.moneymate.bottomNav.BottomBar
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.domain.Transaction
import pt.isel.moneymate.domain.TransactionType
import pt.isel.moneymate.home.HomeScreen
import pt.isel.moneymate.profile.ProfileScreen
import pt.isel.moneymate.statistics.StatisticsScreen
import pt.isel.moneymate.transactions.TransactionsScreen
import java.util.*

@Composable
fun MainScreen() {
    var bottomState by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = { BottomBar(
            onHomeRequested = { bottomState = "Home" },
            onTransactionsRequested = { bottomState = "Transactions"},
            onStatisticsRequested = { bottomState = "Statistics"},
            onProfileRequested = { bottomState = "Profile"}
        ) },
        backgroundColor = Color(0xFF1A1421)
    ){ innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                when (bottomState) {
                    "Home" -> HomeScreen()
                    "Transactions" -> TransactionsScreen(transactions)
                    "Statistics" -> StatisticsScreen()
                    "Profile" -> ProfileScreen()
                }
            }
        }
    }
}

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
        date = Date()
    ),
    Transaction(
        type = TransactionType.INCOME,
        description = "Salary",
        category = Category("Work"),
        amount = 5678.9,
        date = Date()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Movie ticket",
        category = Category("Entertainment"),
        amount = 9.99,
        date = Date()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Lunch",
        category = Category("Food"),
        amount = 12.34,
        date = Date()
    ),
    Transaction(
        type = TransactionType.INCOME,
        description = "Salary",
        category = Category("Work"),
        amount = 5678.9,
        date = Date()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Movie ticket",
        category = Category("Entertainment"),
        amount = 9.99,
        date = Date()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Lunch",
        category = Category("Food"),
        amount = 12.34,
        date = Date()
    ),
    Transaction(
        type = TransactionType.INCOME,
        description = "Salary",
        category = Category("Work"),
        amount = 5678.9,
        date = Date()
    ),
    Transaction(
        type = TransactionType.EXPENSE,
        description = "Movie ticket",
        category = Category("Entertainment"),
        amount = 9.99,
        date = Date()
    )
)