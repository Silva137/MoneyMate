package pt.isel.moneymate.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pt.isel.moneymate.home.HomeViewModel
import pt.isel.moneymate.profile.ProfileViewModel
import pt.isel.moneymate.statistics.StatisticsViewModel
import pt.isel.moneymate.transactions.TransactionsViewModel
import pt.isel.moneymate.utils.bottomNavigation.BottomBarScreen
import pt.isel.moneymate.utils.bottomNavigation.BottomNavGraph
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    homeViewModel: HomeViewModel,
    transactionsViewModel: TransactionsViewModel,
    statisticsViewModel: StatisticsViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        BottomNavGraph(
            navController = navController,
            homeViewModel = homeViewModel,
            transactionsViewModel = transactionsViewModel,
            statisticsViewModel = statisticsViewModel,
            profileViewModel = profileViewModel
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Transactions,
        BottomBarScreen.Statistics,
        BottomBarScreen.Profile
        )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier
            .height(85.dp)
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(25.dp)),
        backgroundColor = Color(0xFF2C2633),
        contentColor = Color(0xFFFFFFFF),
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon",
                modifier = Modifier.size(30.dp)
            )
        },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}