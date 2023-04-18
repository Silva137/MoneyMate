package isel.pt.moneymate.controller.models

import isel.pt.moneymate.domain.Category
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.User

data class GetTransactions (
   val transactions: List<Transaction>
)

data class TransactionsDto(
   val transactions: List<Transaction>
)

// TODO()
data class TransactionDto(
   val x: Int
)

data class WalletBalanceDTO(
   val lucrativeSum: Int,
   val expensesSum: Int,
)

data class CategorySumsOutDto(
   val category: Category,
   val amount: Int
)

data class UserSumsOutDto(
   val user: User,
   val amount: Int
)