package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.Transaction
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository {

    /*
     id               SERIAL PRIMARY KEY,
    user_id          INT            NOT NULL REFERENCES MoneyMate.users (id),
    wallet_id        INT            NOT NULL REFERENCES MoneyMate.wallet (id),
    category_id      INT            NOT NULL REFERENCES MoneyMate.category (id),
    amount           DECIMAL(10, 2) NOT NULL,
    date_of_creation DATE           NOT NULL DEFAULT CURRENT_DATE,
    title            VARCHAR(50)    NOT NULL,
    transaction_type VARCHAR(15)    NOT NULL,
    periodical       SMALLINT,
     */

    @SqlUpdate(
        """
        INSERT INTO MoneyMate.transactions(user_id, wallet_id, category_id, amount, title, transaction_type, periodical) 
        VALUES (:user_id,:wallet_id, :category_id, :amount, :title, :transaction_type, :periodical)
        """
    )
    @GetGeneratedKeys("id")
    fun createTransaction(
        @Bind("user_id") userId: Int,
        @Bind("wallet_id") walletId: Int,
        @Bind("category_id") categoryId: Int,
        @Bind("amount") amount: Int,
        @Bind("title") title: String,
        @Bind("transaction_type") transactionType: Int,
        @Bind("periodical") periodical: String
    ): Int

    @SqlQuery(
        """
        SELECT *
        FROM MoneyMate.wallet wallets
        JOIN MoneyMate.transactions transactions ON wallets.id = transactions.wallet_id
        WHERE wallets.id = :wallet_id
    """
    )
    fun getTransactionsByDate(@Bind("wallet_id") walletId: Int): List<Transaction>

    @SqlQuery(
        """
        SELECT *
        FROM MoneyMate.wallet wallets
        JOIN MoneyMate.transactions transactions ON wallets.id = transactions.wallet_id
        WHERE wallets.id = :wallet_id
    """
    )
    fun getTransactionsByPrice(@Bind("wallet_id") walletId: Int): List<Transaction>

    @SqlQuery(
        """
        SELECT *
        FROM MoneyMate.wallet wallets
        JOIN MoneyMate.transactions transactions ON wallets.id = transactions.wallet_id
        WHERE wallets.id = :wallet_id
    """
    )
    fun getTransactionsByCategory(@Bind("wallet_id") walletId: Int): List<Transaction>

    fun getTransactionsGivenWalletsOfUser(userId: Int): List<Transaction> {
        TODO()

    }

    fun getTransactionById(transactionId: Int): Transaction {
        TODO()

    }

    fun updateTransaction(walletId: Int, ammount: Int, title: String): Transaction {
        TODO()

    }

    fun deleteTransaction(transactionId: Int){
        TODO()
    }

    fun getPositiveSumsFromWallet(walletId: Int): Int {
        TODO("Not yet implemented")
    }

    fun getNegativeSumsFromWallet(walletId: Int): Int {
        TODO("Not yet implemented")
    }

    fun getTransactionsFromPWGivenCategory(walletId: Int, categoryId: String): List<Transaction> {
        TODO("Not yet implemented")
    }

    fun getAmountsFromPwByCategory(walletId: Int): Map<Int, Int> {
        TODO("Not yet implemented")
    }

    fun getTransactionsFromSwGivenUser(walletId: Int, userId: Int): List<Transaction> {
        TODO("Not yet implemented")
    }

    fun getAmountsFromSwByUser(walletId: Int): Map<Int, Int> {
        TODO("Not yet implemented")
    }

    fun getPositiveSumsFromWallets(): Int {
        TODO("Not yet implemented")
    }

    fun getNegativeSumsFromWallets(): Int {
        TODO("Not yet implemented")
    }

    fun getTransactionsFromAllWalletsGivenCategory(categoryId: String): List<Transaction> {
        TODO("Not yet implemented")
    }

    fun getAmountsFromAllWalletsByCategory(): Map<Int, Int> {
        TODO("Not yet implemented")
    }


}