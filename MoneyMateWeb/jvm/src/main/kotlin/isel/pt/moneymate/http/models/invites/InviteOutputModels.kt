package isel.pt.moneymate.http.models.invites

import isel.pt.moneymate.domain.*
import isel.pt.moneymate.http.models.categories.CategoryDTO
import isel.pt.moneymate.http.models.categories.toDTO
import isel.pt.moneymate.http.models.users.UserDTO
import isel.pt.moneymate.http.models.users.toDTO
import isel.pt.moneymate.http.models.wallets.WalletDTO
import isel.pt.moneymate.http.models.wallets.toDTO
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

data class InviteDTO (
    val id: Int,
    @field:NotNull(message = "Sender User is required")
    val sender: UserDTO,
    @field:NotNull(message = "Sender User is required")
    val receiver: UserDTO,
    @field:NotNull(message = "Wallet is required")
    val sharedWallet: WalletDTO,
    @field:NotNull(message = "State is required")
    val state: InviteState,
    @field:NotNull(message = "Finihes is required")
    val onFinished: Boolean,
    @field:DateTimeFormat //(pattern = "dd-MM-yyyy")
    val createdAt: Date,
){
    constructor(invite: Invite): this(
        invite.id,
        invite.sender.toDTO(),
        invite.receiver.toDTO(),
        invite.sharedWallet.toDTO(),
        invite.state,
        invite.onFinished,
        invite.createdAt,
    )
}

fun Invite.toDTO() = InviteDTO(this)


data class InvitesDTO (
    val invites: List<InviteDTO>,
    @field:Digits(integer = 10, fraction = 0, message = "Total count must be a number")
    val totalCount: Int = invites.size
)

fun List<Invite>.toDTO(): InvitesDTO {
    val invites = this.map { it.toDTO() }
    return InvitesDTO(invites)
}

data class AllInvitesDTO(
    val send: InvitesDTO,
    val received: InvitesDTO
)
