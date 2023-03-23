package pt.isel.moneymate.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.moneymate.background.BackGround
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.ui.theme.green
import pt.isel.moneymate.ui.theme.purple

@Composable
fun WelcomeScreen() {
    BackGround()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 110.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        GradientButton(text = "Get Started", onClick = { TODO() })
    }
}


@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() })
    {
        Box(
            modifier = Modifier
                .background(Brush.horizontalGradient(colors = listOf(purple, green)))
                .padding(horizontal = 36.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = poppins
            )
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen()
}