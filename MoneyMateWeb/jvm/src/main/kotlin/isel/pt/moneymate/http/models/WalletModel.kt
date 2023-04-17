package isel.pt.moneymate.http.models

import isel.pt.moneymate.services.dtos.WalletDTO
import jakarta.validation.constraints.NotBlank

data class WalletInputDTO(
    @field:NotBlank(message = "Wallet name  is required")
    val name: String,
){
    fun toWalletInputDTO() = WalletDTO(name)
}
