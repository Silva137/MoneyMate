package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.domain.Wallet
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository {

    @SqlQuery(
        """
        SELECT w.user_id
        FROM MoneyMate.wallet w 
        WHERE w.wallet_id = :wallet_id
        """
    )
    fun getUserOfPW(
        @Bind("wallet_id") walletId: Int
    ): Int?

    @SqlQuery(
        """
        SELECT sw.user_id
        FROM MoneyMate.user_shared_wallet sw 
        WHERE sw.wallet_id = :wallet_id AND sw.user_id = :user_id
        """
    )
    fun getUserOfSW(
        @Bind("user_id") userId: Int,
        @Bind("wallet_id") walletId: Int
    ): Int?

    @SqlQuery(
        """
    SELECT
        (SELECT COUNT(*) FROM MoneyMate.wallet w WHERE w.wallet_name = :wallet_name AND w.user_id = :user_id)
        +
        (SELECT COUNT(*) FROM MoneyMate.user_shared_wallet sw WHERE sw.sh_name = :wallet_name AND sw.user_id = :user_id)
        > 0
    """
    )
    fun verifyNameExistence(
        @Bind("wallet_name") name: String,
        @Bind("user_id") userId: Int
    ): Boolean

    @SqlQuery(
        """
        SELECT COUNT(*) > 0
        FROM MoneyMate.user_shared_wallet sw 
        WHERE sw.wallet_id = :wallet_id
        """
    )
    fun hasUsersInSW(
        @Bind("wallet_id") walletId: Int
    ): Boolean

    @SqlUpdate("INSERT INTO MoneyMate.wallet(wallet_name, user_id) VALUES (:name,:user_id)")
    @GetGeneratedKeys("wallet_id")
    fun createWallet(@Bind("name") walletName: String, @Bind("user_id") userId: Int): Int


    @SqlUpdate("INSERT INTO MoneyMate.user_shared_wallet(wallet_id, user_id, sh_name) VALUES (:wallet_id, :user_id, :name)")
    @GetGeneratedKeys("sh_id")
    fun createWalletUserAssociation(
        @Bind("user_id") userId: Int,
        @Bind("wallet_id") walletId: Int,
        @Bind("name") walletName: String
    ): Int


    @SqlQuery("""
        SELECT w.*, u.*
        FROM MoneyMate.wallet w 
        JOIN MoneyMate.users u ON w.user_id = u.user_id 
        WHERE w.wallet_id = :id
        """)
    fun getWalletById(@Bind("id") walletId: Int): Wallet?

    @SqlQuery("""
        SELECT sw.sh_name
        FROM MoneyMate.user_shared_wallet sw 
        WHERE sw.wallet_id = :wallet_id AND sw.user_id = :user_id
        """)
    fun getSWNameForUser(
        @Bind("user_id") userId: Int,
        @Bind("wallet_id") walletId: Int
    ): String?

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.wallet w
        JOIN MoneyMate.users u ON w.user_id = u.user_id
        WHERE w.user_id = :id
        ORDER BY w.wallet_name

        LIMIT :limit OFFSET :offset
   """)
    fun getPWOfUser(@Bind("id") userId: Int, @Bind("offset") offset: Int, @Bind("limit") limit: Int): List<Wallet>?

    @SqlQuery("""
       SELECT 
        w.wallet_id,
        sw.sh_name as wallet_name,
        w.date_of_creation,
        u_w.user_id,
        u_w.username,
        u_w.email,
        u_w.password     
        FROM MoneyMate.user_shared_wallet sw
            JOIN MoneyMate.wallet w ON sw.wallet_id = w.wallet_id
            JOIN MoneyMate.users u_w ON w.user_id = u_w.user_id
            JOIN MoneyMate.users u_sw ON sw.user_id = u_sw.user_id
        WHERE sw.user_id = :user_id
        ORDER BY w.wallet_name
        LIMIT :limit OFFSET :offset
   """)
    fun getSWOfUser(
        @Bind("user_id") userId: Int,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Wallet>?

    @SqlQuery("""
       SELECT *
        FROM MoneyMate.user_shared_wallet sw
            JOIN MoneyMate.users u ON sw.user_id = u.user_id
        WHERE sw.wallet_id = :shared_wallet_id
        ORDER BY sw.sh_id
   """)
    fun getUsersOfSW(
        @Bind("shared_wallet_id") sharedWalletId: Int,
    ): List<User>?

    @SqlUpdate("UPDATE MoneyMate.wallet SET wallet_name = :name WHERE wallet_id = :id ")
    fun updatePW(@Bind("name") newName: String, @Bind("id") walletId: Int)

    @SqlUpdate("""
        UPDATE MoneyMate.user_shared_wallet 
        SET sh_name = :wallet_name 
        WHERE wallet_id = :wallet_id AND user_id = :user_id
    """)
    fun updateSW(
        @Bind("wallet_name") newName: String,
        @Bind("wallet_id") walletId: Int,
        @Bind("user_id") userId: Int
    )

    @SqlUpdate("DELETE FROM MoneyMate.wallet WHERE wallet_id = :id")
    fun deleteWallet(@Bind("id") walletId: Int)

    @SqlUpdate("DELETE FROM MoneyMate.user_shared_wallet WHERE wallet_id = :wallet_id AND user_id = :user_id")
    fun deleteAssociation(
        @Bind("user_id") userId: Int,
        @Bind("wallet_id") walletId: Int,
    )

    @SqlQuery("""
        SELECT SUM(transactions.amount)
        FROM MoneyMate.transactions transactions
        WHERE transactions.wallet_id = :wallet_id
    """)
    fun getWalletBalance(@Bind("wallet_id") walletId: Int): Int
}

