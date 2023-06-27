package pt.isel.moneymate.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import pt.isel.moneymate.domain.User
import pt.isel.moneymate.services.wallets.models.Wallet
import androidx.lifecycle.viewmodel.compose.viewModel



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    wallets: List<Wallet>,
    selectedWalletId: Int?,
    onWalletSelected: (Int) -> Unit = {},
    onProfileClick: () -> Unit = {},
    onTransactionClick: () -> Unit = {}
) {
    Log.d("HomeScreen", "Rendering HomeScreen")

    var showPopup by remember { mutableStateOf(false) }
    val (bottomState, setBottomState) = remember { mutableStateOf("Home") }

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
                onClick = { showPopup = true }
            )
            DateRow(date = "December 2023")
            MonthReport()
        }
    }

    if (showPopup) {
        WalletSelectionPopup(
            wallets = wallets,
            selectedWalletId = selectedWalletId,
            onWalletSelected = onWalletSelected
        ) {
            showPopup = false
        }
    }
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
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "$ ${selectedWallet?.balance ?: 0}",
                fontSize = 30.sp,
                color = Color.White,
                textAlign = TextAlign.Start
            )
            Text(
                text = "** ** ** 1234",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun DateRow(date: String) {
    // Rest of the code...
}

@Composable
fun MonthReport(
    expenses: Double = 20.00,
    income: Double = 135.00
) {
    // Rest of the code...
}


@Composable
fun WalletSelectionPopup(
    wallets: List<Wallet>,
    selectedWalletId: Int?,
    onWalletSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val dismissDialog = {
        onDismiss()
    }

    AlertDialog(
        onDismissRequest = dismissDialog,
        title = {
            Text(text = "Select Wallet")
        },
        buttons = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                wallets.forEach { wallet ->
                    Button(
                        onClick = {
                            onWalletSelected(wallet.id)
                            dismissDialog()
                        },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = wallet.name)
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun MonthReportView() {
    MonthReport()
}

@Preview
@Composable
fun DateRowPreview() {
    DateRow("December 2023")
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
        onProfileClick = { },
        onTransactionClick = {}
    )
}
