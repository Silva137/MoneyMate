package pt.isel.moneymate.transactions

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pt.isel.moneymate.R
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.domain.Transaction
import pt.isel.moneymate.domain.TransactionType
import pt.isel.moneymate.theme.expenseRed
import pt.isel.moneymate.theme.incomeGreen
import java.time.LocalDateTime
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionsScreen(
    transactions: List<Transaction> = listOf(),
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    selectedTransaction: MutableState<Transaction?>,
) {

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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transactions",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
                IconButton(onClick = { /* handle search click */ }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }
            TransactionsList(
                transactions = transactions,
                scope = scope,
                bottomSheetState = bottomSheetState,
                selectedTransaction = selectedTransaction
            )
        }
    }
}

@Composable
fun BottomSheetContent(selectedTransaction: MutableState<Transaction?>) {
    var editedTransaction by remember { mutableStateOf(selectedTransaction.value) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Edit transaction")
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = editedTransaction?.description.orEmpty(),
            onValueChange = { editedTransaction = editedTransaction?.copy(description = it) },
            label = { Text("Description") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = editedTransaction?.amount?.toString().orEmpty(),
            onValueChange = { editedTransaction = editedTransaction?.copy(amount = it.toDoubleOrNull() ?: 0.0) },
            label = { Text("Amount") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { selectedTransaction.value = null },
                modifier = Modifier.weight(1f)
            ) {
                Text("Delete")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    selectedTransaction.value = editedTransaction
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionsList(
    transactions: List<Transaction>,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    selectedTransaction: MutableState<Transaction?>
    ) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(transactions) { item ->
            TransactionItem(item, scope, bottomSheetState,selectedTransaction)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionItem(
    transaction: Transaction,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    selectedTransaction: MutableState<Transaction?>
) {
    val imageResource = if (transaction.type == TransactionType.EXPENSE) R.drawable.expense_item else R.drawable.income_item
    val isExpense = transaction.type == TransactionType.EXPENSE


    Box(
        modifier = Modifier
            .width(315.dp)
            .height(80.dp)
            .clickable {
                selectedTransaction.value = transaction
                scope.launch {
                    bottomSheetState.show()
                }
            }
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


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun TransactionItemPreview() {
    val transaction = Transaction(
        type = TransactionType.EXPENSE,
        description = "none",
        category = Category("Food"),
        amount = 12.34,
        date = LocalDateTime.now()
    )
    TransactionsScreen(
        transactions = listOf(transaction),
        scope = MainScope(),
        bottomSheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden),
        selectedTransaction = mutableStateOf(null)
    )
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun TransactionsListPreview() {
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
    TransactionsScreen(
        transactions = transactions,
        scope = MainScope(),
        bottomSheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden),
        selectedTransaction = mutableStateOf(null)
    )
}


