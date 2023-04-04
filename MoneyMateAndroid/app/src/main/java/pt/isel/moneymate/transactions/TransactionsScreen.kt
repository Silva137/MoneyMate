package pt.isel.moneymate.transactions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.moneymate.domain.Transaction
import pt.isel.moneymate.R
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.domain.TransactionType
import pt.isel.moneymate.home.BankCard
import pt.isel.moneymate.home.DateRow
import pt.isel.moneymate.home.MonthReport
import pt.isel.moneymate.theme.expenseRed
import pt.isel.moneymate.theme.incomeGreen
import java.util.*

@Composable
fun TransactionsScreen(transactions: List<Transaction> = listOf()) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Transactions",
                color = Color.White,
                fontSize = 28.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
            )
            TransactionsList(transactions = transactions)
        }
    }
}

@Composable
fun TransactionsList(transactions: List<Transaction>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(transactions) { transaction ->
            TransactionItem(transaction)
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    val imageResource = if (transaction.type == TransactionType.EXPENSE) R.drawable.expense_item else R.drawable.income_item
    val isExpense = transaction.type == TransactionType.EXPENSE

    Box(
        modifier = Modifier
            .width(315.dp)
            .height(80.dp),
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Expenses",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.End)
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_user),
                    contentDescription = "Category Icon",
                    modifier = Modifier.size(32.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = transaction.category.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "03 Apr 2023",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = if(isExpense) "-${transaction.amount}" else "+${transaction.amount}",
                color = if (isExpense) expenseRed else incomeGreen,
                fontSize = 20.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}


@Preview
@Composable
fun TransactionItemPreview() {
    val transaction = Transaction(
        type = TransactionType.EXPENSE,
        description = "none",
        category = Category("Food"),
        amount = 12.34,
        date = Date()
    )
    TransactionItem(transaction = transaction)
}

@Preview
@Composable
fun TransactionsListPreview() {
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

    TransactionsScreen(transactions = transactions)
}


