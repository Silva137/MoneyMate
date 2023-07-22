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
    var amount: Int
)

data class UserPayment(
    val sender: User,
    val receiver: User,
    val amount: Int
)

data class UserPayments(
    val paymentsToSend: MutableMap<User, Int>,
    val paymentsToReceive: MutableMap<User, Int>
)

data class WalletPayments(
    val average: Int,
    val paymentMapper:Map<User, UserPayments>
)
