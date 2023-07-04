package pt.isel.moneymate.statistics

import DatePicker
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.moneymate.R
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.domain.Transaction
import pt.isel.moneymate.profile.AddWallet
import pt.isel.moneymate.profile.BalanceTexts
import pt.isel.moneymate.profile.ProfImg
import pt.isel.moneymate.profile.ProfileButton
import pt.isel.moneymate.services.category.models.CategoryBalanceDTO
import pt.isel.moneymate.services.category.models.CategoryDTO
import pt.isel.moneymate.services.users.models.UserDTO
import pt.isel.moneymate.theme.*
import pt.isel.moneymate.utils.getCurrentYearRange
import java.time.LocalDate

@Composable
fun StatisticsScreen(
    categoriesBalancePos: List<CategoryBalanceDTO>?,
    categoriesBalanceNeg: List<CategoryBalanceDTO>?,
    onSearchClick: (startDate: LocalDate, endDate: LocalDate) -> Unit = { _, _ -> }
){

    var pickedStartDate by remember { mutableStateOf(getCurrentYearRange().first) }
    var pickedEndDate by remember { mutableStateOf(LocalDate.now()) }
    var isSearchClicked by remember { mutableStateOf(false) }
    var selectedButton by remember { mutableStateOf(ToggleButtonState.Negative) }


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
                    .padding(start = 30.dp, top = 15.dp),
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
            if(selectedButton == ToggleButtonState.Positive)
                categoriesBalancePos?.let { createPieChartData(it) }
            else categoriesBalanceNeg?.let { createPieChartData(it) }

            PieChart(data = piechartData ?: mapOf(), animDuration = 1000)
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
    data: Map<String, Int>,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 1000,
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    // add the colors as per the number of data(no. of pie chart entries)
    // so that each data will get a color
    val colors = listOf(
        Purple200,
        Purple500,
        Teal200,
        Purple700,
        Blue
    )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }

        // To see the data in more structured way
        // Compose Function in which Items are showing data
        DetailsPieChart(
            data = data,
            colors = colors
        )

    }

}

@Composable
fun DetailsPieChart(
    data: Map<String, Int>,
    colors: List<Color>
) {
    Column(
        modifier = Modifier
            .padding(top = 80.dp)
            .fillMaxWidth()
    ) {
        // create the data items
        data.values.forEachIndexed { index, value ->
            DetailsPieChartItem(
                data = Pair(data.keys.elementAt(index), value),
                color = colors[index]
            )
        }

    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Int>,
    height: Dp = 45.dp,
    color: Color
) {

    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 40.dp),
        color = Color.Transparent
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

@Preview
@Composable
fun PreviewStatisticsScreen() {
    StatisticsScreen(
        categoriesBalancePos = listOf(
            CategoryBalanceDTO(CategoryDTO(1, "Food", UserDTO(1,"silva","silva")), 100),
            CategoryBalanceDTO(CategoryDTO(2, "Transport",UserDTO(1,"silva","silva")), 200),
            CategoryBalanceDTO(CategoryDTO(3, "Entertainment", UserDTO(1,"silva","silva")), 300),
            CategoryBalanceDTO(CategoryDTO(4, "Other", UserDTO(1,"silva","silva")), 400),
        ),
        categoriesBalanceNeg = listOf(
            CategoryBalanceDTO(CategoryDTO(1, "Food", UserDTO(1,"silva","silva")), -100),
            CategoryBalanceDTO(CategoryDTO(2, "Transport",UserDTO(1,"silva","silva")), -200),
            CategoryBalanceDTO(CategoryDTO(3, "Entertainment", UserDTO(1,"silva","silva")), -300),
            CategoryBalanceDTO(CategoryDTO(4, "Other", UserDTO(1,"silva","silva")), -400),
        )
    )
}