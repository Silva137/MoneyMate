package pt.isel.moneymate.utils.bottomNavigation

import pt.isel.moneymate.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Categories : BottomBarScreen(
        route = "categories",
        title = "Categories",
        icon = R.drawable.icon_categories
    )

    object Transactions : BottomBarScreen(
        route = "transactions",
        title = "Transactions",
        icon = R.drawable.icon_transactions
    )

    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.icon_home
    )

    object Statistics : BottomBarScreen(
        route = "statistics",
        title = "Statistics",
        icon = R.drawable.icon_statistics
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = R.drawable.icon_profile
    )
}