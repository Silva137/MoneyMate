package isel.pt.moneymate.http.controller

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.http.models.invites.CreateInviteDTO
import isel.pt.moneymate.http.models.invites.UpdateInviteDTO
import isel.pt.moneymate.http.models.wallets.UpdateWalletDTO
import isel.pt.moneymate.http.utils.Uris
import isel.pt.moneymate.services.InviteService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
class InviteController(
    private val inviteService: InviteService
    ) {


    @PostMapping(Uris.Invites.CREATE)
    fun createInvite(
        @AuthenticationPrincipal senderUser: User,
        @Valid @RequestBody inviteData: CreateInviteDTO,
        @PathVariable walletId: Int
    ): ResponseEntity<*> {
        val invite = inviteService.createInvite(
            senderUser,
            inviteData,
            walletId
        )
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(invite)
    }

    @PatchMapping(Uris.Invites.UPDATE)
    fun updateWalletName(
        @AuthenticationPrincipal receiverUser: User,
        @Valid @RequestBody inviteData: UpdateInviteDTO,
        @PathVariable inviteId: Int
    ) : ResponseEntity<*> {
        val wallet = inviteService.updateInviteStatus(
            receiverUser,
            inviteData,
            inviteId
        )
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(wallet)
    }


    @GetMapping(Uris.Invites.GET_ALL)
    fun getInvites(
        @AuthenticationPrincipal user: User,
    ) : ResponseEntity<*> {
        val invites = inviteService.getAllInvites(user)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(invites)
    }
}


