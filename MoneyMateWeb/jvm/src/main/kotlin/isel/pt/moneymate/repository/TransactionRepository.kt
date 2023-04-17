package isel.pt.moneymate.repository

import isel.pt.moneymate.controller.models.CategorySumsOutDto
import isel.pt.moneymate.controller.models.UserSumsOutDto
import isel.pt.moneymate.domain.Transaction
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository {

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
        FROM MoneyMate.transactions transactions 
        WHERE transactions.transaction_id = :transaction_id
    """
    )
    fun getTransactionById(
        @Bind("transaction_id") transactionId: Int
    ): Transaction?

    @SqlUpdate(
        """
        UPDATE MoneyMate.transactions
        SET category_id = :category_id, amount=: amount, title = :title, transaction_type=:transaction_type
        WHERE transactions.transaction_id = :transaction_id
    """
    )
    fun updateTransaction(
        @Bind("transaction_id") transactionId: Int,
        @Bind("category_id") categoryId: Int,
        @Bind("amount") amount: Int,
        @Bind("title") title: String,
        @Bind("transaction_type") transactionType: Int,
    )

    @SqlUpdate(
        """
        DELETE FROM MoneyMate.transactions
        WHERE transactions.transaction_id = :transaction_id
    """
    )
    fun deleteTransaction(
        @Bind("transaction_id") transactionId: Int,
    )

    @SqlQuery(
        """
        SELECT *
        FROM MoneyMate.wallet wallets
        JOIN MoneyMate.transactions transactions ON wallets.wallet_id = transactions.wallet_id
        WHERE wallets.wallet_id = :wallet_id
        ORDER BY 
            CASE WHEN :criterion = 'bydate' THEN transactions.date_of_creation END [:order],
            CASE WHEN :criterion = 'byprice' THEN transactions.amount END [:order]
    """
    )
    fun getTransactionsSortedBy(
        @Bind("wallet_id") walletId: Int,
        @Bind("criterion") criterion: String,
        @Bind("order") order: String // Must be ASC OR DESC
    ): List<Transaction>


    @SqlQuery(
        """
        SELECT 
            SUM( CASE WHEN transactions.amount >=0 THEN transactions.amount ELSE 0 END) AS lucrative_sum,
            SUM( CASE WHEN transactions.amount < 0 THEN transactions.amount ELSE 0 END) AS expense_sum
        FROM MoneyMate.wallet wallets
        JOIN MoneyMate.transactions transactions ON wallets.wallet_id = transactions.wallet_id
        WHERE wallets.wallet_id = :wallet_id
    """
    )
    fun getSumsFromWallet(
        @Bind("wallet_id") walletId: Int
    ): List<Int>


    @SqlQuery(
        """
        SELECT *
        FROM MoneyMate.transactions transactions
        JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
        WHERE transactions.wallet_id = :wallet_id AND transactions.category_id = :category_id
        ORDER BY transactions.date_of_creation DESC
    """
    )
    fun getTransactionsFromPWGivenCategory(
        @Bind("wallet_id") walletId: Int,
        @Bind("category_id") categoryId: Int
    ): List<Transaction>


    @SqlQuery(
        """
        SELECT categories.*, SUM(transactions.amount)
        FROM MoneyMate.transactions transactions
        JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
        WHERE transactions.wallet_id = :wallet_id AND transactions.category_id = :category_id
        GROUP BY categories.category_id
    """
    )
    fun getAmountsFromPwByCategory(
        @Bind("wallet_id") walletId: Int,
    ): List<CategorySumsOutDto>


    @SqlQuery(
        """
        SELECT *
        FROM MoneyMate.transactions transactions
        JOIN MoneyMate.users users ON transactions.user_id = users.user_id
        WHERE transactions.wallet_id = :wallet_id AND transactions.user_id = :user_id
        ORDER BY transactions.date_of_creation DESC
    """
    )
    fun getTransactionsFromSwGivenUser(
        @Bind("wallet_id") walletId: Int,
        @Bind("user_id") userId: Int,
    ): List<Transaction>

    @SqlQuery(
        """
        SELECT users.*, SUM(transactions.amount)
        FROM MoneyMate.transactions transactions
        JOIN MoneyMate.users users ON transactions.user_id =  users.user_id
        WHERE transactions.wallet_id = :wallet_id AND transactions.user_id = :user_id
        GROUP BY users.user_id
    """
    )
    fun getAmountsFromSwByUser(
        @Bind("wallet_id") walletId: Int,
    ): List<UserSumsOutDto>



    /*
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

     */

    /**
    @SqlQuery(
    """
    SELECT *
    FROM MoneyMate.wallet wallets
    JOIN MoneyMate.transactions transactions ON wallets.wallet_id = transactions.wallet_id
    WHERE wallets.wallet_id = :wallet_id
    ORDER BY transactions.date_of_creation DESC
    """
    )
    fun getTransactionsByDateDesc(
    @Bind("wallet_id") walletId: Int
    ): List<Transaction>

    @SqlQuery(
    """
    SELECT *
    FROM MoneyMate.wallet wallets
    JOIN MoneyMate.transactions transactions ON wallets.wallet_id = transactions.wallet_id
    WHERE wallets.wallet_id = :wallet_id
    ORDER BY transactions.date_of_creation ASC
    """
    )
    fun getTransactionsByDateAsc(@Bind("wallet_id") walletId: Int): List<Transaction>

    @SqlQuery(
    """
    SELECT *
    FROM MoneyMate.wallet wallets
    JOIN MoneyMate.transactions transactions ON wallets.wallet_id = transactions.wallet_id
    WHERE wallets.wallet_id = :wallet_id
    ORDER BY transactions.amount ASC
    """
    )
    fun getTransactionsByPriceAsc(@Bind("wallet_id") walletId: Int): List<Transaction>

    @SqlQuery(
    """
    SELECT *
    FROM MoneyMate.wallet wallets
    JOIN MoneyMate.transactions transactions ON wallets.wallet_id = transactions.wallet_id
    WHERE wallets.wallet_id = :wallet_id
    ORDER BY transactions.amount DESC
    """
    )
    fun getTransactionsByPriceDesc(@Bind("wallet_id") walletId: Int): List<Transaction>

    @SqlQuery(
    """
    SELECT SUM(amount)
    FROM MoneyMate.wallet wallets
    JOIN MoneyMate.transactions transactions ON wallets.wallet_id = transactions.wallet_id
    WHERE wallets.wallet_id = :wallet_id AND amount > 0
    ORDER BY transactions.amount DESC
    """
    )
     */


}