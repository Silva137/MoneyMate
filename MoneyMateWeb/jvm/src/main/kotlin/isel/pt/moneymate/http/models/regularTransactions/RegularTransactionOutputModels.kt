import isel.pt.moneymate.controller.models.TransactionDTO
import isel.pt.moneymate.controller.models.toDTO
import isel.pt.moneymate.domain.Invite
import isel.pt.moneymate.domain.RegularTransaction
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.http.models.categories.CategoryDTO
import isel.pt.moneymate.http.models.categories.toDTO
import isel.pt.moneymate.http.models.invites.InviteDTO
import isel.pt.moneymate.http.models.invites.InvitesDTO
import isel.pt.moneymate.http.models.invites.toDTO
import isel.pt.moneymate.http.models.users.UserDTO
import isel.pt.moneymate.http.models.users.toDTO
import isel.pt.moneymate.http.models.wallets.WalletDTO
import isel.pt.moneymate.http.models.wallets.toDTO
import jakarta.validation.constraints.Digits
import java.time.LocalDateTime

data class RegularTransactionDTO(
    val id: Int,
    val frequency: String,
    val transactionInfo: TransactionDTO
) {
    constructor(regularTransaction: RegularTransaction) : this(
        regularTransaction.id,
        regularTransaction.frquency,
        regularTransaction.transactionInfo.toDTO()
    )
}

fun RegularTransaction.toDTO() = RegularTransactionDTO(this)


data class AllRegularTransactionsDTO (
    val regularTransactions: List<RegularTransactionDTO>,
    @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
    val totalCount: Int = regularTransactions.size
)

fun List<RegularTransaction>.toDTO(): AllRegularTransactionsDTO {
    val regularTransactions = this.map { it.toDTO() }
    return AllRegularTransactionsDTO(regularTransactions)
}
