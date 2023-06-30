package pt.isel.moneymate.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import pt.isel.moneymate.R
import pt.isel.moneymate.domain.User
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.services.transactions.models.WalletBalanceDTO
import pt.isel.moneymate.services.wallets.models.Wallet
import pt.isel.moneymate.theme.dialogBackground
import pt.isel.moneymate.theme.expenseRed
import pt.isel.moneymate.theme.incomeGreen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    wallets: List<Wallet>,
    categories: List<Category>,
    selectedWalletId: Int?,
    onWalletSelected: (Int) -> Unit = {},
    walletBalance: WalletBalanceDTO,
    onCategoriesDropdownClick: () -> Unit,
) {
    Log.d("HomeScreen", "Rendering HomeScreen")

    var showPopupSelectWallet by remember { mutableStateOf(false) }
    var showPopupAddTransaction by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize() // Occupy the entire available space
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize() // Fill the entire available space
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BankCard(
                selectedWallet = wallets.find { it.id == selectedWalletId },
                onClick = { showPopupSelectWallet = true }
            )
            DateRow()
            MonthReport( walletBalance.expenseSum, walletBalance.incomeSum)
            AddTransactionButton(onClick = { showPopupAddTransaction = true })
        }
    }

    if (showPopupSelectWallet) {
        WalletSelectionPopup(
            wallets = wallets,
            selectedWalletId = selectedWalletId,
            onWalletSelected = onWalletSelected,
            onDismiss = { showPopupSelectWallet = false }
        )
    }

    if (showPopupAddTransaction) {
        AddTransactionPopup(
            categories = categories,
            onDismiss = { showPopupAddTransaction = false },
            onCategoriesDropdownClick = onCategoriesDropdownClick
        )
    }
}



@Composable
private fun AddTransactionButton(
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = R.drawable.add_transaction),
        contentDescription = "Background",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .padding(top = 55.dp)
            .height(85.dp)
            .width(85.dp)
            .clickable { onClick() }
    )
}

@Composable
fun BankCard(
    selectedWallet: Wallet?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 25.dp, bottom = 10.dp, start = 25.dp, end = 25.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.bank_card),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clickable { onClick() }
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomStart),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Balance",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "€ ${selectedWallet?.balance ?: 0}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "*** *** *** 1234",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun DateRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, top = 25.dp, bottom = 5.dp)
    ) {
        Text(
            text = "Month Report",
            fontSize = 22.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
fun MonthReport(
    expenses: Double,
    income: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.expenses_home),
                contentDescription = "Expenses",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "-$expenses€",
                color = expenseRed,
                fontSize = 24.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.income_home),
                contentDescription = "Income",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "+$income€",
                color = incomeGreen,
                fontSize = 24.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun AddTransactionPopup(
    categories: List<Category>,
    onDismiss: () -> Unit,
    onCategoriesDropdownClick: () -> Unit
){
    var transactionTitle by remember { mutableStateOf("") }
    var transactionAmount by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableStateOf(-1) }


    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            color = dialogBackground,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Create Transaction",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = transactionTitle,
                    onValueChange = { transactionTitle = it },
                    label = { Text(text = "Title", color = Color.White) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        textColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = transactionAmount,
                    onValueChange = { transactionAmount = it },
                    label = { Text(text = "Amount", color = Color.White) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        textColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                LargeDropdownMenu(
                    label = "Select a category",
                    items = categories.map { it.name },
                    selectedIndex = selectedIndex,
                    onItemSelected = { index, _ -> selectedIndex = index },
                    onCategoriesDropdownClicked = onCategoriesDropdownClick
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            // Handle the logic for creating the transaction
                            // You can access the transactionTitle and transactionAmount here
                            // and perform the necessary actions (e.g., save to a database)
                            onDismiss()
                        }
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}



@Composable
fun WalletSelectionPopup(
    wallets: List<Wallet>,
    selectedWalletId: Int?,
    onWalletSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(
        onDismissRequest = { onDismiss()},
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            color = dialogBackground,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Select a Wallet",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                wallets.forEach { wallet ->
                    WalletListItem(
                        wallet = wallet,
                        isSelected = wallet.id == selectedWalletId,
                        onWalletSelected = onWalletSelected
                    )
                    Divider(color = Color.DarkGray, thickness = 3.dp)
                }
            }
        }
    }
}


//TODO: and if the walletList is too long?
@Composable
fun WalletListItem(
    wallet: Wallet,
    isSelected: Boolean,
    onWalletSelected: (Int) -> Unit
) {
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onWalletSelected(wallet.id) }
    ) {
        Text(
            text = wallet.name,
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = poppins,
                fontWeight = fontWeight,
                color = Color.White
            )
        )
        if (isSelected) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Selected",
                tint = incomeGreen
            )
        }
    }
}

@Composable
fun <T> LargeDropdownMenu(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    notSetLabel: String? = null,
    items: List<T>,
    selectedIndex: Int = -1,
    onItemSelected: (index: Int, item: T) -> Unit,
    selectedItemToString: (T) -> String = { it.toString() },
    drawItem: @Composable (T, Boolean, Boolean, () -> Unit) -> Unit = { item, selected, itemEnabled, onClick ->
        LargeDropdownMenuItem(
            text = item.toString(),
            selected = selected,
            enabled = itemEnabled,
            onClick = onClick,
        )
    },
    onCategoriesDropdownClicked: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = items.getOrNull(selectedIndex)?.let { selectedItemToString(it) } ?: "",
            onValueChange = { },
            label = { Text(text = label, color = Color.White) },
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
            trailingIcon = { Icon(Icons.Filled.ArrowDropUp, "trailingIcon", tint = Color.White) },
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                textColor = Color.White
            )
        )
        // Transparent clickable surface on top of OutlinedTextField
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable(enabled = enabled) {
                    expanded = true
                    onCategoriesDropdownClicked()
                },
            color = Color.Transparent,
        ) { }
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false },
        ) {
            Surface(
                modifier.height(450.dp),
                color = dialogBackground,
                shape = RoundedCornerShape(8.dp)
            ) {
                val listState = rememberLazyListState()
                if (selectedIndex > -1) {
                    LaunchedEffect("ScrollToSelected") {
                        listState.scrollToItem(index = selectedIndex)
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                    if (notSetLabel != null) {
                        item {
                            LargeDropdownMenuItem(
                                text = notSetLabel,
                                selected = false,
                                enabled = false,
                                onClick = { },
                            )
                        }
                    }
                    itemsIndexed(items) { index, item ->
                        val selectedItem = index == selectedIndex
                        drawItem(item, selectedItem, true) {
                            onItemSelected(index, item)
                            expanded = false
                        }

                        if (index < items.lastIndex) {
                            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.DarkGray, thickness = 3.dp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LargeDropdownMenuItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = when {
        selected -> incomeGreen
        else -> Color.White
    }
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(modifier = Modifier
            .clickable(enabled) { onClick() }
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                text = text,
            )
        }
    }

}


@Preview
@Composable
fun MonthReportView() {
    MonthReport( 100.0, 200.0)
}

@Preview
@Composable
fun BankCardPreview() {
    BankCard(selectedWallet = null, onClick = {})
}

@Preview
@Composable
fun HomeScreenPreview() {
    var selectedWalletId by remember { mutableStateOf(1) }

    HomeScreen(
        wallets = listOf(
            Wallet(1, "Wallet 1", User(1,"shimi","shimi"), "2023-06-01", 1000),
            Wallet(2, "Wallet 2",  User(1,"shimi","shimi"), "2023-06-01", 2000),
            Wallet(3, "Wallet 3",  User(1,"shimi","shimi"), "2023-06-01", 3000)
        ),
        selectedWalletId = selectedWalletId,
        onWalletSelected = { walletId ->
            selectedWalletId = walletId
        },
        walletBalance = WalletBalanceDTO(22.00, 1300.0),
        categories = listOf(
            Category(1, "Saude", User(1,"silva","silva")),
            Category(2, "Desporto", User(1,"silva","silva")),
            Category(3, "Carro", User(1,"silva","silva"))
        ),
        onCategoriesDropdownClick = {},
    )
}
