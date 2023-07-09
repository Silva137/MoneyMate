package pt.isel.moneymate.statistics

import DatePicker
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import pt.isel.moneymate.R
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.domain.Transaction
import pt.isel.moneymate.services.category.models.CategoryBalanceDTO
import pt.isel.moneymate.theme.*
import pt.isel.moneymate.transactions.TransactionItem
import pt.isel.moneymate.transactions.TransactionsList
import pt.isel.moneymate.utils.getCurrentYearRange
import java.time.LocalDate
import java.util.*

@Composable
fun StatisticsScreen(
    state: StatisticsViewModel.StatisticsState,
    categoriesBalancePos: List<CategoryBalanceDTO>,
    categoriesBalanceNeg: List<CategoryBalanceDTO>,
    categoryTransactionsNeg: List<Transaction>,
    categoryTransactionsPos: List<Transaction>,
    onSearchClick: (startDate: LocalDate, endDate: LocalDate) -> Unit = { _, _ -> },
    errorMessage: String?,
    onCategoryClick: ( categoryId: Int, startDate: LocalDate, endDate: LocalDate) -> Unit = { _, _, _ -> },
){


    var pickedStartDate by remember { mutableStateOf(getCurrentYearRange().first) }
    var pickedEndDate by remember { mutableStateOf(LocalDate.now()) }
    var isSearchClicked by remember { mutableStateOf(false) }
    var selectedButton by remember { mutableStateOf(ToggleButtonState.Negative) }
    var showPopupCategoryTransactions by remember { mutableStateOf(false) }



    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state) {
        when (state) {
            StatisticsViewModel.StatisticsState.ERROR -> {
                val message = errorMessage ?: "An error occurred "
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
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
                    .padding(start = 25.dp, top = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Statistics",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )

                ToggleButton(
                    onPositiveClick = { selectedButton = ToggleButtonState.Positive },
                    onNegativeClick = { selectedButton = ToggleButtonState.Negative },
                    selectedButton = selectedButton
                )

                IconButton(onClick = {
                    onSearchClick(pickedStartDate, pickedEndDate)
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
            DatePicker(
                onStartDateSelected = { pickedStartDate = it},
                onEndDateSelected = { pickedEndDate = it},
                startDate = pickedStartDate,
                endDate = pickedEndDate
            )

            val piechartData =
            if(selectedButton == ToggleButtonState.Positive) createPieChartData(categoriesBalancePos)
            else createPieChartData(categoriesBalanceNeg)

            if(piechartData.isEmpty()){
                Text(
                    text = "No statistics found",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                )
            }
            else PieChart(
                showCategoryTransactionsClick = { categoryName ->
                    val category = if (selectedButton == ToggleButtonState.Negative)categoriesBalanceNeg.map { it.category }.first { it.name == categoryName }
                    else categoriesBalancePos.map { it.category }.first { it.name == categoryName }
                    showPopupCategoryTransactions = true
                    onCategoryClick(category.id, pickedStartDate, pickedEndDate)
                },
                data = piechartData,
                animDuration = 1000
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(bottom = 85.dp)
        ) { snackbarData ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { snackbarHostState.currentSnackbarData?.dismiss() }) {
                        Text(text = "Dismiss", color = Color.White)
                    }
                }
            ) {
                Text(text = snackbarData.message, color = Color.White)
            }
        }
    }

    if(showPopupCategoryTransactions){
        val list = if (selectedButton == ToggleButtonState.Positive) categoryTransactionsPos
        else categoryTransactionsNeg
        CategoryTransactionsPopup(
            categoryTransactions = list,
            onDismiss = {
                showPopupCategoryTransactions = false

            }
        )
    }
}

@Composable
fun CategoryTransactionsPopup(
    categoryTransactions: List<Transaction>,
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
            modifier = Modifier
                .height(500.dp),
            color = dialogBackground,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Category Transactions", //TODO: change to category name
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                val selectedTransaction = remember { mutableStateOf<Transaction?>(null) }
                CategoryTransactionsList(transactions = categoryTransactions, selectedTransaction = selectedTransaction)

            }
        }
    }
}

@Composable
fun CategoryTransactionsList(
    transactions: List<Transaction>,
    selectedTransaction: MutableState<Transaction?>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(transactions) { item ->
            TransactionItem(item)
        }
    }
}


@Composable
fun ToggleButton(
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    selectedButton: ToggleButtonState
) {
    Row(Modifier.padding(8.dp)) {
        Button(
            onClick = { onNegativeClick() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selectedButton == ToggleButtonState.Negative) expenseRed else dialogBackground
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "-",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Button(
            onClick = { onPositiveClick() },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selectedButton == ToggleButtonState.Positive) incomeGreen else dialogBackground
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "+",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

enum class ToggleButtonState {
    Positive,
    Negative
}


@Composable
fun PieChart(
    showCategoryTransactionsClick: (String) -> Unit = {},
    data: Map<String, Int>,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 1000,
) {
    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors = generateColors(data.size) // Generate colors based on the number of items

    var animationPlayed by remember { mutableStateOf(false) }
    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index], // Use dynamically generated colors
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }

        PieChartList(
            showCategoryTransactionsClick = showCategoryTransactionsClick,
            data = data,
            colors = colors
        )
    }
}

@Composable
fun generateColors(count: Int): List<Color> {
    val colors = mutableListOf<Color>()
    val pastelColors = listOf(
        Color(0xFF9176C4), // Lavender
        Color(0xFF80C3FA), // Light Blue
        Color(0xFF8DDB90), // Light Green
        Color(0xFFFAA767), // Light Orange
        Color(0xFFFF6D68), // Light Salmon
        Color(0xFFB0BEC5), // Blue Grey
        Color(0xFFFFF179), // Light Yellow
        Color(0xFF5FB1A9), // Light Teal
        Color(0xFFD36ED1)  // Light Purple
    )

    repeat(count) { index ->
        val color = pastelColors[index % pastelColors.size]
        colors.add(color)
    }
    return colors
}

@Composable
fun PieChartList(
    showCategoryTransactionsClick: (String) -> Unit = {},
    data: Map<String, Int>,
    colors: List<Color>
) {
    LazyColumn(
        modifier = Modifier.padding(bottom = 85.dp, top = 25.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        data.forEach { (key, value) ->
            item {
                PieChartItem(
                    showCategoryTransactionsClick = showCategoryTransactionsClick,
                    data = Pair(key, value),
                    color = colors[data.keys.indexOf(key)]
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PieChartItem(
    showCategoryTransactionsClick: (String) -> Unit = {},
    data: Pair<String, Int>,
    height: Dp = 45.dp,
    color: Color
) {
    Surface(
        modifier = Modifier.padding(start = 30.dp),
        color = Color.Transparent,
        onClick = { showCategoryTransactionsClick(data.first)}
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppins,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppins,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

        }

    }
}

fun createPieChartData(categoryBalanceList: List<CategoryBalanceDTO>): Map<String, Int> {
    return categoryBalanceList.associate { it.category.name to it.balance }
}
