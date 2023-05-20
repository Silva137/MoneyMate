package isel.pt.moneymate.domain

data class WalletBalance(
    val incomeSum: Int, // TODO verify: Float or INT
    val expenseSum: Int,
)

data class CategoryBalance(
    val category: Category,
    val amount: Int
)

data class UserBalance(
    val user: User,
    val amount: Int
)
