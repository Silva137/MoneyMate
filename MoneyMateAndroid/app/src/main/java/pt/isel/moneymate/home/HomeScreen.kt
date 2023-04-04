package pt.isel.moneymate.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import pt.isel.moneymate.theme.expenseRed
import pt.isel.moneymate.theme.incomeGreen

@Composable
fun HomeScreen() {
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
            BankCard()
            DateRow(date = "December 2023")
            MonthReport()
        }
    }
}

@Composable
fun DateRow(date: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { /* TODO: Move date to the previous month */ }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Previous month",
                tint = Color.White
            )
        }
        Text(
            text = date,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(horizontal = 12.dp),
            color = Color.White
        )
        IconButton(onClick = { /* TODO: Move date to the next month */ }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = "Next month",
                tint = Color.White
            )
        }
    }
}

@Composable
fun BankCard(
    balanceMoneyText: Int = 0
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
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomStart),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Balance",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$ $balanceMoneyText",
                    fontSize = 22.sp,
                    fontFamily = poppins,
                    color = Color.White,
                )
                Text(
                    text = "** ** ** 1234",
                    fontSize = 18.sp,
                    fontFamily = poppins,
                    color = Color.White,)
            }
        }
    }
}

@Composable
fun MonthReport(
    expenses: Double = 20.00,
    income: Double = 135.00
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp),
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
                text = "-$expenses",
                color = expenseRed,
                fontSize = 20.sp,
                fontFamily = poppins,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(start = 80.dp, end = 35.dp)
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
                text = "+$income",
                color = incomeGreen,
                fontSize = 20.sp,
                fontFamily = poppins,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(start = 80.dp,end = 35.dp)
            )
        }
    }
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
    BankCard()
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
