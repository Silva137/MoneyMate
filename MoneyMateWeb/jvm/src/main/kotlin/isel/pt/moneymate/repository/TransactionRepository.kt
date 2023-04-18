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
        INSERT INTO MoneyMate.transactions(user_id, wallet_id, category_id, amount, title, periodical) 
        VALUES (:user_id,:wallet_id, :category_id, :amount, :title, :periodical)
        """
    )
    @GetGeneratedKeys("transaction_id")
    fun createTransaction(
        @Bind("user_id") userId: Int,
        @Bind("wallet_id") walletId: Int,
        @Bind("category_id") categoryId: Int,
        @Bind("amount") amount: Int,
        @Bind("title") title: String,
        @Bind("periodical") periodical: Int
    ): Int

    // transactions.*, users.*, wallet.*, category.*
    @SqlQuery(
        """
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.transaction_id = :transaction_id
    """
    )
    fun getTransactionById(
        @Bind("transaction_id") transactionId: Int
    ): Transaction?

    @SqlUpdate(
        """
        UPDATE MoneyMate.transactions
        SET category_id = :category_id, amount= :amount, title = :title
        WHERE transactions.transaction_id = :transaction_id
    """
    )
    fun updateTransaction(
        @Bind("transaction_id") transactionId: Int,
        @Bind("category_id") categoryId: Int,
        @Bind("amount") amount: Int,
        @Bind("title") title: String,
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

    // Por omissao e ASC
    // JOINSSS
    @SqlQuery(
        """
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id
       ORDER BY 
            CASE WHEN :criterion = 'bydate' AND :order = 'DESC' THEN transactions.date_of_creation END DESC,
            CASE WHEN :criterion = 'bydate' AND :order = 'ASC' THEN transactions.date_of_creation END,
            CASE WHEN :criterion = 'byprice' AND :order = 'DESC' THEN transactions.amount END DESC,
            CASE WHEN :criterion = 'byprice' AND :order = 'ASC' THEN transactions.amount END
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
        SELECT categories.*, SUM(transactions.amount) AS sum
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
}