package isel.pt.moneymate.controller.models

import isel.pt.moneymate.services.dtos.WalletDTO
import jakarta.validation.constraints.NotBlank

data class WalletInputModel(
    @field:NotBlank(message = "Wallet name  is required")
    val name: String,
){
    fun toWalletInputDTO() = WalletDTO(name)
}
