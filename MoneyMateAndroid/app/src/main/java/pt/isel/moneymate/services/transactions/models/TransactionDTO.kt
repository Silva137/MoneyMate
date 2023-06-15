package pt.isel.moneymate.services.transactions.models

import pt.isel.moneymate.services.category.models.CategoryDTO
import pt.isel.moneymate.services.users.models.UserDTO
import pt.isel.moneymate.services.wallets.models.WalletDTO
import java.time.LocalDateTime

class TransactionDTO (
    val id: Int,
    val title: String,
    val amount: Float,
    val user: UserDTO,
    val wallet: WalletDTO,
    val category: CategoryDTO,
    val createdAt: LocalDateTime,
    val periodical: Int
)