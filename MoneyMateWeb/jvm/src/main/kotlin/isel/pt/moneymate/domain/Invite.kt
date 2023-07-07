package isel.pt.moneymate.domain

import java.sql.Date


fun String.toInviteState(): InviteState? {
    return enumValues<InviteState>().find { it.name.equals(this, ignoreCase = true) }
}

enum class InviteState {
    ACCEPTED,
    PENDING,
    REJECTED
}

data class Invite(
    val id: Int,
    val sender: User,
    val receiver: User,
    val sharedWallet: Wallet,
    val state: InviteState,
    val onFinished: Boolean,
    val createdAt : Date
)
