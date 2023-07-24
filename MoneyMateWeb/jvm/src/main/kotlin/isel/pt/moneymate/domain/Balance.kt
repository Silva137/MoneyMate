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

// Correct

// Represents a Payment
data class Payment(
    val user: UserDTO, // Either represents the user to send the money to, or the user to receive the money
    val ammount: Int
)

// Payments that the logged User must send or receive
data class UserPayments(
    val average: Int,
    val paymentsToSend: MutableList<Payment>,
    val paymentsToReceive: MutableList<Payment>,
)


// TODO ALL DTOS below DELETE test purposes
// Temprary class to test the algortim
data class UserPaymentsTemporary(
    val average: Int,
    val paymentsToSend: MutableList<Payment>,
    val paymentsToReceive: MutableList<Payment>,
    val mapToDelete: MutableMap<User, UserPaymentsMapper>
)

data class UserPaymentsMapper(
    val paymentsToSend: MutableMap<User, Int>,
    val paymentsToReceive: MutableMap<User, Int>
)


