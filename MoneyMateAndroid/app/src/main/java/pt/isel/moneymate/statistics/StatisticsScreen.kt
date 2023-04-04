package pt.isel.moneymate.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun StatisticsScreen() {

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        Text(text = "Statistics", fontSize = 20.sp)
    }

}