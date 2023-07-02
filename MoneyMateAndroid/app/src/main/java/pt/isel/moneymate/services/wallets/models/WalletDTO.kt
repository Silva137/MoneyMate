package pt.isel.moneymate.services.wallets.models

import java.util.Date
import pt.isel.moneymate.services.users.models.UserDTO

data class WalletDTO (
    val id: Int,
    val name: String,
    val user: UserDTO,
    val createdAt: String,
)