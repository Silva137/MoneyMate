package pt.isel.moneymate.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.moneymate.R

@Composable
fun BottomBar(
        onHomeRequested: () -> Unit = {},
        onTransactionsRequested: () -> Unit = {},
        onStatisticsRequested: () -> Unit = {},
        onProfileRequested: () -> Unit = {}
    ) {
    val (selectedItem, setSelectedItem) = remember { mutableStateOf("Home") }

    BottomNavigation(
        modifier = Modifier
            .height(85.dp)
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(25.dp)),
        backgroundColor = Color(0xFF2C2633),
        contentColor = Color(0xFFFFFFFF),
    ) {

        BottomNavigationItem(
            selected = selectedItem == "Home",
            onClick = {setSelectedItem("Home");onHomeRequested()},
            icon = { Icon(painter = painterResource(id = R.drawable.icon_home), contentDescription = null, modifier = Modifier.size(30.dp)) }
        )
        BottomNavigationItem(
            selected = selectedItem == "Transactions",
            onClick = {setSelectedItem("Transactions");onTransactionsRequested()},
            icon = { Icon(painter = painterResource(id = R.drawable.icon_transactions), contentDescription = null, modifier = Modifier.size(30.dp))}
        )
        BottomNavigationItem(
            selected = selectedItem == "Statistics",
            onClick = {setSelectedItem("Statistics");onStatisticsRequested()},
            icon = { Icon(painter = painterResource(id = R.drawable.icon_statistics), contentDescription = null, modifier = Modifier.size(30.dp))}
        )
        BottomNavigationItem(
            selected = selectedItem == "Profile",
            onClick = {setSelectedItem("Profile");onProfileRequested()},
            icon = { Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = null, modifier = Modifier.size(30.dp))}
        )
    }
}

/*
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NavGraph(navController: NavHostController, ) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("transactions") { TransactionsScreen() }
        composable("statistics") { StatisticsScreen() }
        composable("profile") { ProfileScreen() }
    }
}*/

@Preview()
@Composable
fun BottomAppBarPreview() {
    Scaffold(
        bottomBar = { BottomBar() }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(text = "Hello World")
        }
    }
}
