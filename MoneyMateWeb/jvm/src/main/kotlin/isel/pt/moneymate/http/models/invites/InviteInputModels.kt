package isel.pt.moneymate.http.models.invites

import isel.pt.moneymate.domain.InviteState
import jakarta.validation.constraints.NotBlank

data class UpdateInviteDTO(
    @field:NotBlank(message = "Message State is required")
    val state: String,
)


data class CreateInviteDTO (
    @field:NotBlank(message = "Username is required")
    val receiverUserName: String,
)
