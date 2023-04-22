package isel.pt.moneymate.http.models.wallets

import jakarta.validation.constraints.NotBlank

data class CreateWalletDTO(
    @field:NotBlank(message = "Wallet name  is required")
    val name: String,
)

data class UpdateWalletDTO (
    @field:NotBlank(message = "Wallet name  is required")
    val name: String,
)