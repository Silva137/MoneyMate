package isel.pt.moneymate.services

import isel.pt.moneymate.domain.InviteState
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.domain.toInviteState
import isel.pt.moneymate.exceptions.AlreadyExistsException
import isel.pt.moneymate.exceptions.InvalidParameterException
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.exceptions.UnauthorizedException
import isel.pt.moneymate.http.models.invites.*
import isel.pt.moneymate.http.models.wallets.WalletsWithBalanceDTO
import isel.pt.moneymate.http.models.wallets.toDTO
import isel.pt.moneymate.repository.InviteRepository
import isel.pt.moneymate.repository.UsersRepository
import isel.pt.moneymate.repository.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class InviteService(
    private val walletRepository: WalletRepository,
    private val inviteRepository: InviteRepository,
    private val usersRepository: UsersRepository
){

    /**
     * Available Requests
     */

    fun createInvite(senderUser: User, messageInput: CreateInviteDTO, sharedWallet: Int): InviteDTO{
        val receiverName = messageInput.receiverUserName
        val receiverUser = usersRepository.getUserByUsername(receiverName)
            ?: throw NotFoundException("Receiver User with Name $receiverName not found")
        verifyInviteExistence(receiverUser.id, sharedWallet)
        verifyUserInSW(senderUser.id, sharedWallet)
        // TODO VER SE NAO É O PROPRIO USER A MANDAR PARA ELE MESMO
        val createdId = inviteRepository.createInvite(senderUser.id, receiverUser.id, sharedWallet)
        return getInviteById(createdId)
    }

    fun updateInviteStatus(receiverUser: User, inviteInput: UpdateInviteDTO, inviteId: Int): InviteDTO {
        val newInviteState = inviteInput.state.toInviteState()
            ?: throw InvalidParameterException("Bad parameter state")
        isReceiverUser(receiverUser.id, inviteId)
        val message = getInviteById(inviteId)
        verifyState(inviteId, message.state)
        inviteRepository.updateInvite(inviteId, newInviteState.name) // Fechar o pedido
        val updatedInvite = getInviteById(inviteId)

        if (newInviteState == InviteState.ACCEPTED)
            // Nova Associação fica com o nome da wallet de quem convidou
            walletRepository.createWalletUserAssociation(receiverUser.id,
                updatedInvite.sharedWallet.id,  updatedInvite.sharedWallet.name )

        return updatedInvite
    }

    fun getAllInvites(user: User): AllInvitesDTO{
        val received = getAllReceivedInvites(user)
        val send = getAllSendInvites(user)
        return AllInvitesDTO(send, received)
    }


    /**
     * Aux
     */

    // Can become a separate request later
    fun getAllReceivedInvites(user: User): InvitesDTO {
        val invites = inviteRepository.getAllReceivedInvites(user.id)
            ?: throw NotFoundException("No received invites found for user with id $user.id")
        return invites.toDTO()
    }

    // Can become a separate request later
    fun getAllSendInvites(user: User): InvitesDTO {
        val invites = inviteRepository.getAllSendInvites(user.id)
            ?: throw NotFoundException("No received invites found for user with id $user.id")
        return invites.toDTO()
    }

    fun getInviteById(inviteId: Int): InviteDTO {
        val invite = inviteRepository.getInvitetById(inviteId)
            ?: throw NotFoundException("Invite with id $inviteId not found")
        return invite.toDTO()
    }


    // Verify if user has permitions to invite
    fun verifyUserInSW(userId: Int, walletId: Int) {
        val userOfWallet = walletRepository.getUserOfSW(userId, walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found or User Has no Permitions")

        if(userOfWallet != userId)
            throw UnauthorizedException("User does not have permission to perform this action on Wallet $walletId")
    }

    // To avoid spam messages
    fun verifyInviteExistence(receiverId: Int, sharedWallet: Int){
        if (inviteRepository.verifyInviteExistence(receiverId, sharedWallet)) {
            throw AlreadyExistsException("Invite for wallet $sharedWallet already exists for user $receiverId")
        }
    }

    fun verifyState(inviteId: Int, state: InviteState){
        if(state != InviteState.PENDING)
            throw AlreadyExistsException("Message with id $inviteId Was already ${state.name}")
    }

    // Only Receiver can Modify Invite State
    fun isReceiverUser(userId: Int, inviteId: Int) {
        val receiverUser = inviteRepository.getReceiver(inviteId)
            ?: throw NotFoundException("Message with id $inviteId not found")

        if(receiverUser != userId)
            throw UnauthorizedException("User does not have permission to perform this action on Invite $inviteId")
    }
}


