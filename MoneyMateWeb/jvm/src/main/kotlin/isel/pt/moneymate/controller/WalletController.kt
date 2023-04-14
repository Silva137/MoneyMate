package isel.pt.moneymate.controller

import isel.pt.moneymate.controller.models.WalletInputModel
import isel.pt.moneymate.services.WalletService
import isel.pt.moneymate.utils.Uris
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class WalletController(private val walletsService: WalletService) {


    @PostMapping(Uris.Wallets.CREATE)
    fun createWallet(@Valid @RequestBody walletData: WalletInputModel): ResponseEntity<*> {
        val wallet = walletsService.createWallet(walletData.toWalletInputDTO())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(wallet)
    }

    // TODO: Given autentication get the user wallets
    @GetMapping(Uris.Wallets.GET_WALLETS_OF_USER)
    fun getWallets(): ResponseEntity<*> {
        val wallets = walletsService.getWallets()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(wallets)
    }

    @PatchMapping(Uris.Wallets.UPDATE_NAME)
    fun updateWalletName(@RequestBody walletName: WalletInputModel,  @PathVariable walletId: Int) : ResponseEntity<*> {
        val wallet = walletsService.updateWallet(walletName.toWalletInputDTO(),walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(wallet)
    }
    @DeleteMapping(Uris.Wallets.DELETE_BY_ID)
    fun deleteWallet(@PathVariable walletId: Int) : ResponseEntity<*> {
        walletsService.deleteWallet(walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Wallet with ${walletId} was deleted successfully!")
    }
}


