package isel.pt.moneymate.repository

import isel.pt.moneymate.controller.models.CategoriesBalance
import isel.pt.moneymate.controller.models.UserSumsOutDto
import isel.pt.moneymate.controller.models.WalletBalanceDTO
import isel.pt.moneymate.domain.Transaction
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionRepository {

    @SqlUpdate("""
        INSERT INTO MoneyMate.transactions(title, amount, user_id, wallet_id, category_id, date_of_creation, periodical) 
        VALUES (:title, :amount, :user_id, :wallet_id, :category_id, :date_of_creation, :periodical)
    """)
    @GetGeneratedKeys("transaction_id")
    fun createTransaction(
        @Bind("title") title: String,
        @Bind("amount") amount: Float,
        @Bind("user_id") userId: Int,
        @Bind("wallet_id") walletId: Int,
        @Bind("category_id") categoryId: Int,
        @Bind("date_of_creation") dateOfCreation: LocalDateTime,
        @Bind("periodical") periodical: Int
    ): Int


    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.transaction_id = :transaction_id
    """)
    fun getTransactionById(@Bind("transaction_id") transactionId: Int): Transaction?


    @SqlUpdate("""
        UPDATE MoneyMate.transactions
        SET category_id = :category_id, amount= :amount, title = :title
        WHERE transactions.transaction_id = :transaction_id
    """)
    fun updateTransaction(
        @Bind("transaction_id") transactionId: Int,
        @Bind("category_id") categoryId: Int,
        @Bind("amount") amount: Int,
        @Bind("title") title: String,
    )


    @SqlUpdate("""
        DELETE FROM MoneyMate.transactions
        WHERE transactions.transaction_id = :transaction_id
        """)
    fun deleteTransaction(
        @Bind("transaction_id") transactionId: Int,
    )


    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id
        ORDER BY 
            CASE WHEN :sortedBy = 'bycategory' THEN category.category_name END,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'DESC' THEN transactions.date_of_creation END DESC,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'ASC' THEN transactions.date_of_creation END,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'DESC' THEN transactions.amount END DESC,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'ASC' THEN transactions.amount END
        LIMIT :limit OFFSET :offset
    """)
    fun getTransactionsSortedBy(
        @Bind("wallet_id") walletId: Int,
        @Bind("sortedBy") sortedBy: String,
        @Bind("orderBy") orderBy: String,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?


    @SqlQuery("""
        SELECT 
            SUM( CASE WHEN transactions.amount >=0 THEN transactions.amount ELSE 0 END) AS income_sum,
            SUM( CASE WHEN transactions.amount < 0 THEN transactions.amount ELSE 0 END) AS expense_sum
        FROM MoneyMate.transactions transactions
        WHERE transactions.wallet_id = :wallet_id
    """)
    fun getWalletBalance(@Bind("wallet_id") walletId: Int): WalletBalanceDTO


    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id AND transactions.category_id = :category_id
        ORDER BY transactions.date_of_creation DESC
    """)
    fun getPWTransactionsByCategory(
        @Bind("wallet_id") walletId: Int,
        @Bind("category_id") categoryId: Int
    ): List<Transaction>


    @SqlQuery("""
        SELECT categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password, SUM(transactions.amount) AS sum
            FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
            WHERE transactions.wallet_id = :wallet_id
            GROUP BY categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password
    """)
    fun getPWCategoriesBalance(@Bind("wallet_id") walletId: Int): List<CategoriesBalance>


    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id AND transactions.user_id = :user_id
        ORDER BY transactions.date_of_creation DESC
    """)
    fun getSWTransactionsByUser(
        @Bind("wallet_id") walletId: Int,
        @Bind("user_id") userId: Int,
    ): List<Transaction>


    @SqlQuery("""
        SELECT users.*, SUM(transactions.amount)
        FROM MoneyMate.transactions transactions
        JOIN MoneyMate.users users ON transactions.user_id =  users.user_id
        WHERE transactions.wallet_id = :wallet_id AND transactions.user_id = :user_id
        GROUP BY users.user_id
    """)
    fun getSWUsersBalance(
        @Bind("wallet_id") walletId: Int,
    ): List<UserSumsOutDto>
}