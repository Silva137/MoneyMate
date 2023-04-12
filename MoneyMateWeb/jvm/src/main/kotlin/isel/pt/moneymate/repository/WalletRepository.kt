package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.Wallet
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository


@Repository
interface WalletRepository {
    @SqlUpdate("INSERT INTO MoneyMate.wallet(name,user_id) VALUES (:name,:user_id)")
    @GetGeneratedKeys("id")
    fun createWallet(@Bind("name") walletName: String, @Bind("user_id") userId: Int): Int

    @SqlQuery(
        """
        SELECT w.*, u.*
        FROM MoneyMate.wallet w
        JOIN MoneyMate.users u ON w.user_id = u.id
        WHERE w.user_id = :id
    """
    )
    fun getWallets(@Bind("id") userId: Int): List<Wallet>

    @SqlUpdate("UPDATE MoneyMate.wallet SET name = :name WHERE id = :id ")
    fun updateWallet( @Bind("name") newName: String, @Bind("id") walletId: Int)

    @SqlQuery("SELECT w.*, u.*FROM MoneyMate.wallet w JOIN MoneyMate.users u ON w.user_id = u.id WHERE w.id = :id")
    fun getWalletById(@Bind("id") walletId: Int): Wallet

    @SqlUpdate("DELETE FROM MoneyMate.wallet WHERE id = :id")
    fun deleteWallet(@Bind("id") walletId: Int)
}

