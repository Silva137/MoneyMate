package isel.pt.moneymate.controller.models

import isel.pt.moneymate.services.dtos.CreateWalletDTO
import jakarta.validation.constraints.NotBlank

data class WalletInputModel(
    @field:NotBlank(message = "Wallet name  is required")
    val name: String,
){
    fun toWalletInputDTO() = CreateWalletDTO(name)
}
