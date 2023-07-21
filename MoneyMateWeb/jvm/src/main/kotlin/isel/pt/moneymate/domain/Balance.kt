package isel.pt.moneymate.domain

import isel.pt.moneymate.http.models.users.UserDTO

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
    var amount: Int
)

data class UserPayment(
    val sender: User,
    val receiver: User,
    val amount: Int
)

data class Payments(
    val paymentsToSend: MutableMap<User, Int>,
    val paymentsToReceive: MutableMap<User, Int>
)
