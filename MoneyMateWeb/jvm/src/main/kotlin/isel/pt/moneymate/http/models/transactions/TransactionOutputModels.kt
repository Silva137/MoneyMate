package isel.pt.moneymate.controller.models

import isel.pt.moneymate.domain.CategoryBalance
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.UserBalance
import isel.pt.moneymate.domain.WalletBalance
import isel.pt.moneymate.http.models.categories.CategoryDTO
import isel.pt.moneymate.http.models.users.UserDTO
import isel.pt.moneymate.http.models.wallets.WalletDTO
import jakarta.validation.constraints.Digits
import java.time.LocalDateTime


/** ----------------------------------- Single Transaction --------------------------------   */

data class TransactionDTO(
   val id: Int,
   val title: String,
   val amount: Float,
   val user: UserDTO,
   val wallet: WalletDTO,
   val category: CategoryDTO,
   val createdAt: LocalDateTime,
   val periodical: Int
) {
   constructor(transaction: Transaction) : this(
      transaction.id,
      transaction.title,
      transaction.amount,
      transaction.user.toDTO(),
      transaction.wallet.toDTO(),
      transaction.category.toDTO(),
      transaction.createdAt,
      transaction.periodical
   )
}

fun Transaction.toDTO(): TransactionDTO {
   return TransactionDTO(this)
}

/** ----------------------------------- List of Transactions --------------------------------   */

data class TransactionsDTO(
   val transactions: List<TransactionDTO>,
   @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
   val totalCount: Int = transactions.size
)

fun List<Transaction>.toDTO(): TransactionsDTO {
   val transactions = this.map { it.toDTO() }
   return TransactionsDTO(transactions)
}

/** ----------------------------------- Balance --------------------------------   */

data class WalletBalanceDTO(
   val incomeSum: Int,
   val expenseSum: Int,
){
   constructor(balance: WalletBalance): this(
      balance.incomeSum,
      balance.expenseSum
   )
}
fun WalletBalance.toDTO(): WalletBalanceDTO = WalletBalanceDTO(this)

//////////////////////////////////////////////////////////7

data class UserBalanceDTO(
   val user: UserDTO,
   val amount: Int
){
   constructor(balance: UserBalance): this(
      balance.user.toDTO(),
      balance.amount
   )
}
fun UserBalance.toDTO(): UserBalanceDTO = UserBalanceDTO(this)

data class UsersBalanceDTO(
   val balanceList: List<UserBalanceDTO>,
   @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
   val totalCount: Int = balanceList.size
)
fun List<UserBalance>.toDTO(): UsersBalanceDTO {
   val balances = this.map { it.toDTO() }
   return UsersBalanceDTO(balances)
}

//////////////////////////////////////////////////////////7


data class CategoryBalanceDTO(
   val category: CategoryDTO,
   val balance: Int
){
   constructor(balance: CategoryBalance): this(
      balance.category.toDTO(),
      balance.amount
   )
}
fun CategoryBalance.toDTO(): CategoryBalanceDTO = CategoryBalanceDTO(this)

data class CategoriesBalanceDTO(
   val balanceList: List<CategoryBalanceDTO>,
   @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
   val totalCount: Int = balanceList.size
)
fun List<CategoryBalance>.toDTO(): CategoriesBalanceDTO {
   val balances = this.map { it.toDTO() }
   return CategoriesBalanceDTO(balances)
}


