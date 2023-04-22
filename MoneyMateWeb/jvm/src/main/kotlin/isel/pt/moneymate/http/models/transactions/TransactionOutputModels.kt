package isel.pt.moneymate.controller.models

import isel.pt.moneymate.domain.Category
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.domain.Wallet
import isel.pt.moneymate.http.models.users.UserDTO
import jakarta.validation.constraints.Digits
import java.sql.Date


data class TransactionDTO(
   val id: Int,
   val title: String,
   val amount: Int,
   val user: User,
   val wallet: Wallet,
   val category: Category,
   val createdAt: Date,
   val periodical: Int
)

data class TransactionsDTO(
   val transactions: List<TransactionDTO>,
   @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
   val totalCount: Int = transactions.size
)

data class WalletBalanceDTO(
   val incomeSum: Int,
   val expensesSum: Int,
)

//TODO change to categoryDTO ?
data class CategorySumsOutDto(
   val category: Category,
   val amount: Int
)

//TODO change to userDTO ?
data class UserSumsOutDto(
   val user: User,
   val amount: Int
)