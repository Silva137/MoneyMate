package pt.isel.moneymate.utils.bottomNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pt.isel.moneymate.home.HomeScreen
import pt.isel.moneymate.home.HomeViewModel
import pt.isel.moneymate.profile.ProfileScreen
import pt.isel.moneymate.profile.ProfileViewModel
import pt.isel.moneymate.services.transactions.models.WalletBalanceDTO
import pt.isel.moneymate.statistics.StatisticsScreen
import pt.isel.moneymate.statistics.StatisticsViewModel
import pt.isel.moneymate.transactions.TransactionsScreen
import pt.isel.moneymate.transactions.TransactionsViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    transactionsViewModel: TransactionsViewModel,
    statisticsViewModel: StatisticsViewModel,
    profileViewModel: ProfileViewModel
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            LaunchedEffect(true) {
                homeViewModel.fetchWallets()
            }

            HomeScreen(
                wallets = homeViewModel.wallets,
                selectedWalletId = homeViewModel.selectedWalletId,
                onWalletSelected = { walletId ->
                    // Update the selected wallet ID
                    homeViewModel.selectedWalletId = walletId //TODO change the viewModal logic
                },
                walletBalance = WalletBalanceDTO(22.00, 1300.0),
                categories = homeViewModel.categories,
                onCategoriesDropdownClick = {homeViewModel.fetchCategories()}, //TODO add boolean to see if is already fetched
                onCreateTransactionButtonClick = {categoryId, amount, title ->
                    homeViewModel.createTransaction(homeViewModel.selectedWalletId, categoryId, amount, title)
                }
            )
        }
        composable(route = BottomBarScreen.Transactions.route) {
            TransactionsScreen(
                transactions = transactionsViewModel.transactions,
                onSearchClick = {startTime, endTime ->
                    transactionsViewModel.fetchTransactions(homeViewModel.selectedWalletId,startTime,endTime)
                }
            )
        }
        composable(route = BottomBarScreen.Statistics.route) {
            StatisticsScreen(

            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(
                username = "teste",
                onAddButtonClick = { walletName ->
                    profileViewModel.createWallet(walletName)
                }
            )
        }
    }
}