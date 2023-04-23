package isel.pt.moneymate.controller.models

import isel.pt.moneymate.domain.Category
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.http.models.categories.CategoryDTO
import isel.pt.moneymate.http.models.users.UserDTO
import isel.pt.moneymate.http.models.wallets.WalletDTO
import jakarta.validation.constraints.Digits
import java.time.LocalDateTime


data class TransactionDTO(
   val id: Int,
   val title: String,
   val amount: Float,
   val user: UserDTO,
   val wallet: WalletDTO,
   val category: CategoryDTO,
   val createdAt: LocalDateTime,
   val periodical: Int
)

data class TransactionsDTO(
   val transactions: List<TransactionDTO>,
   @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
   val totalCount: Int = transactions.size
)

data class WalletBalanceDTO(
   val incomeSum: Int,
   val expenseSum: Int,
)

data class CategoriesBalance(
   val category: CategoryDTO,
   val balance: Float
)

data class UserSumsOutDto(
   val user: UserDTO,
   val amount: Int
)