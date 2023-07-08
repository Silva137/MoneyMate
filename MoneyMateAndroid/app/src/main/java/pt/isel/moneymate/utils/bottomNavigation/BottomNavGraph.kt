package pt.isel.moneymate.utils.bottomNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pt.isel.moneymate.category.CategoryViewModel
import pt.isel.moneymate.CategoriesScreen
import pt.isel.moneymate.home.HomeScreen
import pt.isel.moneymate.home.HomeViewModel
import pt.isel.moneymate.profile.ProfileScreen
import pt.isel.moneymate.profile.ProfileViewModel
import pt.isel.moneymate.statistics.StatisticsScreen
import pt.isel.moneymate.statistics.StatisticsViewModel
import pt.isel.moneymate.transactions.TransactionsScreen
import pt.isel.moneymate.transactions.TransactionsViewModel
import pt.isel.moneymate.utils.getCurrentYearRange

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    transactionsViewModel: TransactionsViewModel,
    statisticsViewModel: StatisticsViewModel,
    profileViewModel: ProfileViewModel,
    categoriesViewModel : CategoryViewModel
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            LaunchedEffect(true) {
                homeViewModel.fetchWallets()
            }
            if (homeViewModel.state == HomeViewModel.WalletState.FINISHED) {
                LaunchedEffect(true) { homeViewModel.getWalletBalance() }
            }
            HomeScreen(
                errorMessage = homeViewModel.errorMessage,
                state = homeViewModel.state,
                wallets = homeViewModel.wallets,
                selectedWalletId = homeViewModel.selectedWalletId,
                onWalletSelected = { walletId ->
                    // Update the selected wallet ID
                    homeViewModel.selectedWalletId = walletId //TODO change the viewModal logic
                    homeViewModel.getWalletBalance()
                },
                walletBalance = homeViewModel.balance,
                categories = homeViewModel.categories,
                onCategoriesDropdownClick = {categoriesViewModel.fetchCategories()}, //TODO add boolean to see if is already fetched
                onCreateTransactionButtonClick = {categoryId, amount, title ->
                    homeViewModel.createTransaction(homeViewModel.selectedWalletId, categoryId, amount, title)
                }
            )
        }
        composable(route = BottomBarScreen.Transactions.route) {
            val dateRange = getCurrentYearRange()
            LaunchedEffect(true) {
                transactionsViewModel.fetchTransactions(homeViewModel.selectedWalletId, dateRange.first, dateRange.second, "bydate", "DESC")
            }

            TransactionsScreen(
                errorMessage = transactionsViewModel.errorMessage,
                state = transactionsViewModel.state,
                transactions = transactionsViewModel.transactions,
                onSearchClick = {startTime, endTime, sortedBy, orderBy ->
                    transactionsViewModel.fetchTransactions(homeViewModel.selectedWalletId,startTime,endTime, sortedBy, orderBy)
                }
            )
        }
        composable(route = BottomBarScreen.Statistics.route) {
            val dateRange = getCurrentYearRange()
            LaunchedEffect(true) {
                statisticsViewModel.fetchCategoriesBalance(homeViewModel.selectedWalletId, dateRange.first, dateRange.second)
            }
            StatisticsScreen(
                errorMessage = statisticsViewModel.errorMessage,
                state = statisticsViewModel.state,
                categoriesBalancePos = statisticsViewModel.categoriesBalancePos,
                categoriesBalanceNeg = statisticsViewModel.categoriesBalanceNeg,
                onSearchClick = {startTime, endTime ->
                    statisticsViewModel.fetchCategoriesBalance(homeViewModel.selectedWalletId, startTime, endTime)
                }
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

        composable(route = BottomBarScreen.Categories.route) {
            LaunchedEffect(true) {
                categoriesViewModel.fetchCategories()
            }

            CategoriesScreen(
                userCategories = categoriesViewModel.userCategories,
                systemCategories = categoriesViewModel.systemCategories,
                onEditCategoryClick = { categoryId, updatedCategoryName ->
                    categoriesViewModel.updateCategory(categoryId, updatedCategoryName)
                },
                onDeleteCategoryClick = { categoryId ->
                    categoriesViewModel.deleteCategory(categoryId)
                },
                onCreateCategoryClick = { categoryName ->
                    categoriesViewModel.createCategory(categoryName)
                }
            )
        }
    }
}