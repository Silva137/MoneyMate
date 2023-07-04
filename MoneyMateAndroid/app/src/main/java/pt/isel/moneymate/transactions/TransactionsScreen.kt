package pt.isel.moneymate.transactions

import DatePicker
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
import pt.isel.moneymate.R
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.domain.Transaction
import pt.isel.moneymate.domain.TransactionType
import pt.isel.moneymate.services.users.models.UserDTO
import pt.isel.moneymate.theme.expenseRed
import pt.isel.moneymate.theme.incomeGreen
import pt.isel.moneymate.utils.LargeDropdownMenu
import pt.isel.moneymate.utils.getCurrentYearRange
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun TransactionsScreen(
    transactions: List<Transaction>?,
    onSearchClick: (startDate: LocalDate, endDate: LocalDate, sortedBy: String, orderBy: String) -> Unit = { _, _, _, _ -> }
) {

    var selectedSortedBy by remember { mutableStateOf(0) }
    var selectedOrderBy by remember { mutableStateOf(1) }
    var pickedStartDate by remember { mutableStateOf(getCurrentYearRange().first) }
    var pickedEndDate by remember { mutableStateOf(LocalDate.now()) }
    val selectedTransaction = remember { mutableStateOf<Transaction?>(null) }
    var isSearchClicked by remember { mutableStateOf(false) }

    val sortByOptions = listOf("bydate", "byprice")
    val orderByOptions = listOf("ASC", "DESC")

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
                    .padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 10.dp),
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
                IconButton(onClick = {
                    onSearchClick(pickedStartDate, pickedEndDate, sortByOptions[selectedSortedBy], orderByOptions[selectedOrderBy])
                    isSearchClicked = true
                }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }


            SearchButtons(
                selectedSortedBy = selectedSortedBy,
                selectedOrderBy = selectedOrderBy,
                sortByOptions = sortByOptions,
                orderByOptions = orderByOptions,
                onSortBySelect = { index -> selectedSortedBy = index },
                onOrderBySelect = { index -> selectedOrderBy = index }
            )

            DatePicker(
                onStartDateSelected = { pickedStartDate = it},
                onEndDateSelected = { pickedEndDate = it},
                startDate = pickedStartDate,
                endDate = pickedEndDate
            )

            TransactionsList(
                transactions = transactions,
                selectedTransaction = selectedTransaction
            )
        }
    }
}

@Composable
fun SearchButtons(
    selectedSortedBy: Int,
    selectedOrderBy: Int,
    onSortBySelect: (index: Int) -> Unit,
    onOrderBySelect: (index: Int) -> Unit,
    sortByOptions: List<String>,
    orderByOptions: List<String>
) {

    val sortByOptions = listOf("bydate", "byprice")
    val orderByOptions = listOf("ASC", "DESC")

    Row {
        LargeDropdownMenu(
            modifier = Modifier.width(175.dp),
            label = "Sort By",
            items = sortByOptions,
            selectedIndex = selectedSortedBy,
            onItemSelected = { index, _ -> onSortBySelect(index) },
        )

        Spacer(modifier = Modifier.width(20.dp))

        LargeDropdownMenu(
            modifier = Modifier.width(175.dp),
            label = "Order By",
            items = orderByOptions,
            selectedIndex = selectedOrderBy,
            onItemSelected = { index, _ -> onOrderBySelect(index) },
        )
    }
}

@Composable
fun TransactionsList(
    transactions: List<Transaction>?,
    selectedTransaction: MutableState<Transaction?>
) {
    LazyColumn(
        modifier = Modifier.padding(bottom = 85.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        if (transactions != null) {
            items(transactions) { item ->
                TransactionItem(item, selectedTransaction)
            }
        } else {
            item {
                Text(text = "Loading transactions...")
            }
        }
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    selectedTransaction: MutableState<Transaction?>
) {
    val imageResource = if (transaction.type == TransactionType.EXPENSE) R.drawable.expense_item else R.drawable.income_item
    val isExpense = transaction.type == TransactionType.EXPENSE


    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(80.dp)
            .clickable {
                selectedTransaction.value = transaction
                //more logic here
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
                    painter = painterResource(id = R.drawable.icon_profile),
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
                    text = formatDate(transaction.createdAt),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = if (isExpense) "${transaction.amount}" else "+${transaction.amount}",
                color = if (isExpense) expenseRed else incomeGreen,
                fontSize = 20.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
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
            onValueChange = {
                editedTransaction = editedTransaction?.copy(amount = it.toDoubleOrNull() ?: 0.0)
            },
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


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun TransactionItemPreview() {
    val selectedTransaction = remember { mutableStateOf<Transaction?>(null) }
    val transaction = Transaction(
        type = TransactionType.EXPENSE,
        description = "none",
        category = Category(1, "Saude", UserDTO(1,"silva","silva")),
        amount = 12.34,
        createdAt = LocalDateTime.now()
    )
    TransactionsScreen(
        transactions = listOf(transaction),
        onSearchClick = {startTime, endTime, sortedBy, orderBy ->
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun TransactionsListPreview() {
    val selectedTransaction = remember { mutableStateOf<Transaction?>(null) }
    val transactions = listOf(
        Transaction(
            type = TransactionType.EXPENSE,
            description = "Lunch",
            category = Category(1, "Saude", UserDTO(1,"silva","silva")),
            amount = 12.34,
            createdAt = LocalDateTime.now()
        ),
        Transaction(
            type = TransactionType.INCOME,
            description = "Salary",
            category = Category(1, "Work", UserDTO(1,"silva","silva")),
            amount = 5678.9,
            createdAt = LocalDateTime.now()
        ),
        Transaction(
            type = TransactionType.EXPENSE,
            description = "Movie ticket",
            category = Category(1, "Entertainment", UserDTO(1,"silva","silva")),
            amount = 9.99,
            createdAt = LocalDateTime.now()
        ),
        Transaction(
            type = TransactionType.EXPENSE,
            description = "Lunch",
            category = Category(1, "Food", UserDTO(1,"silva","silva")),
            amount = 12.34,
            createdAt = LocalDateTime.now()
        ),
        Transaction(
            type = TransactionType.INCOME,
            description = "Salary",
            category = Category(1, "Sport", UserDTO(1,"silva","silva")),
            amount = 5678.9,
            createdAt = LocalDateTime.now()
        )
    )
    TransactionsScreen(
        transactions = transactions,
        onSearchClick = {startTime, endTime, sortedBy, orderBy ->
        }
    )
}

fun formatDate(localDateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
    return localDateTime.format(formatter)
}


