package pt.isel.moneymate.services.transactions.models

import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.domain.User
import pt.isel.moneymate.services.category.models.CategoryDTO
import pt.isel.moneymate.services.users.models.UserDTO
import pt.isel.moneymate.services.wallets.models.WalletDTO
import java.time.LocalDateTime

class TransactionDTO (
    val id: Int,
    val title: String,
    val amount: Float,
    val user: User,
    val wallet: WalletDTO,
    val category: Category,
    val createdAt: LocalDateTime,
    val periodical: Int
)

data class WalletBalanceDTO(
    val incomeSum: Double,
    val expenseSum: Double,
)

