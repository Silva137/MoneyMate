package isel.pt.moneymate.http.controller

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.http.models.wallets.CreateWalletDTO
import isel.pt.moneymate.http.models.wallets.UpdateWalletDTO
import isel.pt.moneymate.services.WalletService
import isel.pt.moneymate.http.utils.Uris
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
class WalletController(private val walletsService: WalletService) {


    @PostMapping(Uris.Wallets.CREATE)
    fun createWallet(
        @Valid @RequestBody walletData: CreateWalletDTO,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<*> {
        val wallet = walletsService.createWallet(walletData, user.id)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(wallet)
    }

    @GetMapping(Uris.Wallets.GET_WALLETS_OF_USER)
    fun getWalletsOfUser(
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<*> {
        val wallets = walletsService.getWalletsOfUser(user.id, offset, limit)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(wallets)
    }

    @PatchMapping(Uris.Wallets.UPDATE_NAME)
    fun updateWalletName(@Valid @RequestBody walletName: UpdateWalletDTO, @PathVariable walletId: Int) : ResponseEntity<*> {
        val wallet = walletsService.updateWallet(walletName, walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(wallet)
    }
    @DeleteMapping(Uris.Wallets.DELETE_BY_ID)
    fun deleteWallet(@PathVariable walletId: Int) : ResponseEntity<*> {
        walletsService.deleteWallet(walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Wallet with $walletId was deleted successfully!")
    }
}


