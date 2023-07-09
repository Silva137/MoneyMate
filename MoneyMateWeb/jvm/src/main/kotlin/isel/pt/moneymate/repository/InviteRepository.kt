package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.Invite
import isel.pt.moneymate.domain.Wallet
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface InviteRepository {

    @SqlUpdate("""
        INSERT INTO MoneyMate.invite(sender_id, receiver_id, sw_id) 
        VALUES (:sender_user_id,:receiver_user_id, :shared_wallet_id)
    """)
    @GetGeneratedKeys("invite_id")
    fun createInvite(
        @Bind("sender_user_id") senderUserId: Int,
        @Bind("receiver_user_id") receiverUserId: Int,
        @Bind("shared_wallet_id") sharedWalletId: Int
    ): Int

    @SqlQuery("""
        SELECT *, u1.user_id as user_id_1, u1.username as username1, u1.email as email1, u1.password as password1,
        u2.user_id as user_id_2, u2.username as username2, u2.email as email2, u2.password as password2
        FROM MoneyMate.invite i 
            JOIN MoneyMate.users u1 ON i.receiver_id = u1.user_id
            JOIN MoneyMate.users u2 ON i.sender_id = u2.user_id
            JOIN Moneymate.wallet w ON i.sw_id = w.wallet_id
        WHERE i.invite_id = :invite_id
        """)
    fun getInvitetById(@Bind("invite_id") inviteId: Int): Invite?

    @SqlUpdate("""
        UPDATE MoneyMate.invite 
        SET state = :state, on_finished = true
        WHERE invite_id = :invite_id
    """)
    fun updateInvite(
        @Bind("invite_id") inviteId: Int,
        @Bind("state") inviteState: String,
    )

    @SqlQuery(
        """
        SELECT COUNT(*) > 0
        FROM MoneyMate.invite 
        WHERE receiver_id = :receiver_id AND sw_id = :sw_id
        """
    )
    fun verifyInviteExistence(
        @Bind("receiver_id") receiverId: Int,
        @Bind("sw_id") sw: Int
    ): Boolean

    @SqlQuery(
        """
        SELECT receiver_id
        FROM MoneyMate.invite 
        WHERE invite_id = :invite_id
        """
    )
    fun getReceiver(
        @Bind("invite_id") inviteId: Int
    ): Int?

    @SqlQuery("""
        SELECT *, u1.user_id as user_id_1, u1.username as username1, u1.email as email1, u1.password as password1,
        u2.user_id as user_id_2, u2.username as username2, u2.email as email2, u2.password as password2
        FROM MoneyMate.invite i
            JOIN MoneyMate.users u1 ON i.receiver_id = u1.user_id
            JOIN MoneyMate.users u2 ON i.sender_id = u2.user_id
            JOIN Moneymate.wallet w ON i.sw_id = w.wallet_id
        WHERE i.receiver_id = :id
        ORDER BY i.state = 'PENDING' DESC, i.date_of_creation DESC

   """)
    fun getAllReceivedInvites(@Bind("id") userId: Int): List<Invite>?

    @SqlQuery("""
        SELECT *, u1.user_id as user_id_1, u1.username as username1, u1.email as email1, u1.password as password1,
        u2.user_id as user_id_2, u2.username as username2, u2.email as email2, u2.password as password2
        FROM MoneyMate.invite i
            JOIN MoneyMate.users u1 ON i.receiver_id = u1.user_id
            JOIN MoneyMate.users u2 ON i.sender_id = u2.user_id
            JOIN Moneymate.wallet w ON i.sw_id = w.wallet_id
        WHERE i.sender_id = :id
        ORDER BY i.state = 'PENDING' DESC, i.date_of_creation DESC
   """)
    fun getAllSendInvites(@Bind("id") userId: Int): List<Invite>?

}

