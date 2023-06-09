package isel.pt.moneymate.repository

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
    fun getUserOfWallet(
        @Bind("wallet_id") walletId: Int
    ): Int?

    @SqlUpdate("INSERT INTO MoneyMate.wallet(wallet_name, user_id) VALUES (:name,:user_id)")
    @GetGeneratedKeys("wallet_id")
    fun createWallet(@Bind("name") walletName: String, @Bind("user_id") userId: Int): Int

    @SqlQuery("""
        SELECT w.*, u.*
        FROM MoneyMate.wallet w 
        JOIN MoneyMate.users u ON w.user_id = u.user_id 
        WHERE w.wallet_id = :id
        """)
    fun getWalletById(@Bind("id") walletId: Int): Wallet?

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.wallet w
        JOIN MoneyMate.users u ON w.user_id = u.user_id
        WHERE w.user_id = :id
        LIMIT :limit OFFSET :offset
   """)
    fun getWalletsOfUser(@Bind("id") userId: Int, @Bind("offset") offset: Int, @Bind("limit") limit: Int): List<Wallet>?

    @SqlUpdate("UPDATE MoneyMate.wallet SET wallet_name = :name WHERE wallet_id = :id ")
    fun updateWallet( @Bind("name") newName: String, @Bind("id") walletId: Int)

    @SqlUpdate("DELETE FROM MoneyMate.wallet WHERE wallet_id = :id")
    fun deleteWallet(@Bind("id") walletId: Int)

    @SqlQuery("""
        SELECT SUM(transactions.amount)
        FROM MoneyMate.transactions transactions
        WHERE transactions.wallet_id = :wallet_id
    """)
    fun getWalletBalance(@Bind("wallet_id") walletId: Int): Int
}

