package isel.pt.moneymate.http.controller

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.http.models.wallets.CreateWalletDTO
import isel.pt.moneymate.http.models.wallets.UpdateWalletDTO
import isel.pt.moneymate.http.utils.Consts.DEFAULT_LIMIT
import isel.pt.moneymate.http.utils.Consts.DEFAULT_OFFSET
import isel.pt.moneymate.services.WalletService
import isel.pt.moneymate.http.utils.Uris
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
class WalletController(private val walletsService: WalletService) {


    /** PRIVATE WALLET */

    @PostMapping(Uris.Wallets.CREATE_PW)
    fun createPWallet(
        @Valid @RequestBody walletData: CreateWalletDTO,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<*> {
        val wallet = walletsService.createPrivateWallet(walletData, user.id)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(wallet)
    }

    @GetMapping(Uris.Wallets.GET_PW_OF_USER)
    fun getWalletsOfUser(
        @RequestParam(defaultValue = DEFAULT_OFFSET) offset: Int,
        @RequestParam(defaultValue = DEFAULT_LIMIT) limit: Int,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<*> {
        val wallets = walletsService.getPrivateWalletsOfUser(user.id, offset, limit)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(wallets)
    }

    @PatchMapping(Uris.Wallets.UPDATE_NAME)
    fun updateWalletName(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody walletName: UpdateWalletDTO,
        @PathVariable walletId: Int
    ) : ResponseEntity<*> {
        val wallet = walletsService.updateWallet(user, walletName, walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(wallet)
    }

    @DeleteMapping(Uris.Wallets.DELETE_BY_ID)
    fun deleteWallet(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int
    ) : ResponseEntity<*> {
        walletsService.deletePrivateWallet(user, walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Wallet with $walletId was deleted successfully!")
    }

    /** SHARED WALLET */

    @PostMapping(Uris.Wallets.CREATE_SW)
    fun createSWallet(
        @Valid @RequestBody walletData: CreateWalletDTO,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<*> {
        val wallet = walletsService.createSharedWallet(walletData, user.id)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(wallet)
    }

    @GetMapping(Uris.Wallets.GET_SW_OF_USER)
    fun getSharedWalletsOfUser(
        @RequestParam(defaultValue = DEFAULT_OFFSET) offset: Int,
        @RequestParam(defaultValue = DEFAULT_LIMIT) limit: Int,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<*> {
        val wallets = walletsService.getSharedWalletsOfUser(user.id, offset, limit)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(wallets)
    }

    @GetMapping(Uris.Wallets.GET_USERS_OF_SW)
    fun getMembersOfSW(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int
    ): ResponseEntity<*> {
        val users = walletsService.getMembersOfSW(user, walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(users)
    }

    @DeleteMapping(Uris.Wallets.DELETE_USER_FROM_SW)
    fun deleteUserFromSharedWallet(
        @AuthenticationPrincipal user: User,
        @PathVariable walletId: Int
    ) : ResponseEntity<*> {
        walletsService.deleteUserFromSharedWallet(user, walletId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("User was removed sucedfully from wallet $walletId")
    }
}


