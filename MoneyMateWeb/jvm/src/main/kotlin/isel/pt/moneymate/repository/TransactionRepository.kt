package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.CategoryBalance
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.UserBalance
import isel.pt.moneymate.domain.WalletBalance
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import java.sql.Date
import java.time.LocalDateTime

@Repository
interface TransactionRepository {

    /** ----------------------------------- Transactions --------------------------------   */

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
        @Bind("amount") amount: Float,
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
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY 
            CASE WHEN :sortedBy = 'bycategory' THEN category.category_name END,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'DESC' THEN transactions.date_of_creation END DESC,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'ASC' THEN transactions.date_of_creation END,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'DESC' THEN transactions.amount END DESC,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'ASC' THEN transactions.amount END
        LIMIT :limit OFFSET :offset
    """)
    fun getAllTransactions(
        @Bind("wallet_id") walletId: Int,
        @Bind("sortedBy") sortedBy: String,
        @Bind("orderBy") orderBy: String,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?


    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.user_id = :user_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY 
            CASE WHEN :sortedBy = 'bycategory' THEN category.category_name END,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'DESC' THEN transactions.date_of_creation END DESC,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'ASC' THEN transactions.date_of_creation END,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'DESC' THEN transactions.amount END DESC,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'ASC' THEN transactions.amount END
        LIMIT :limit OFFSET :offset
    """)
    fun getAllTransactionsOfAllWallets(
        @Bind("user_id") userId: Int,
        @Bind("sortedBy") sortedBy: String,
        @Bind("orderBy") orderBy: String,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.amount > 0 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY 
            CASE WHEN :sortedBy = 'bycategory' THEN category.category_name END,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'DESC' THEN transactions.date_of_creation END DESC,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'ASC' THEN transactions.date_of_creation END,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'DESC' THEN transactions.amount END DESC,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'ASC' THEN transactions.amount END
        LIMIT :limit OFFSET :offset
    """)
    fun getIncomeTransactions(
        @Bind("wallet_id") walletId: Int,
        @Bind("sortedBy") sortedBy: String,
        @Bind("orderBy") orderBy: String,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id AND transactions.amount <= 0 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY 
            CASE WHEN :sortedBy = 'bycategory' THEN category.category_name END,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'DESC' THEN transactions.date_of_creation END DESC,
            CASE WHEN :sortedBy = 'bydate' AND :orderBy = 'ASC' THEN transactions.date_of_creation END,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'DESC' THEN transactions.amount END DESC,
            CASE WHEN :sortedBy = 'byprice' AND :orderBy = 'ASC' THEN transactions.amount END
        LIMIT :limit OFFSET :offset
    """)
    fun getExpenseTransactions(
        @Bind("wallet_id") walletId: Int,
        @Bind("sortedBy") sortedBy: String,
        @Bind("orderBy") orderBy: String,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
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
    fun getWalletBalanceOld(@Bind("wallet_id") walletId: Int): WalletBalance?

    /*
    @SqlQuery("""
        SELECT SUM(transactions.amount)
        FROM MoneyMate.transactions transactions
        WHERE transactions.wallet_id = :wallet_id
    """)
    fun getWalletBalance(@Bind("wallet_id") walletId: Int): Int

     */


    /** ----------------------------------- PW --------------------------------   */

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
            JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.category_id = :category_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY transactions.date_of_creation DESC
        LIMIT :limit OFFSET :offset
    """)
    fun getByCategory(
        @Bind("wallet_id") walletId: Int,
        @Bind("category_id") categoryId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
            JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.amount < 0
            AND transactions.category_id = :category_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY transactions.date_of_creation DESC
        LIMIT :limit OFFSET :offset
    """)
    fun getNegByCategory(
        @Bind("wallet_id") walletId: Int,
        @Bind("category_id") categoryId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
            JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.amount > 0
            AND transactions.category_id = :category_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY transactions.date_of_creation DESC
        LIMIT :limit OFFSET :offset
    """)
    fun getPosByCategory(
        @Bind("wallet_id") walletId: Int,
        @Bind("category_id") categoryId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
            JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.user_id = :user_id 
            AND transactions.category_id = :category_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY transactions.date_of_creation DESC
        LIMIT :limit OFFSET :offset
    """)
    fun getByCategoryOfAllWallets(
        @Bind("category_id") categoryId: Int,
        @Bind("user_id") userId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
            JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.user_id = :user_id 
            AND transactions.amount > 0
            AND transactions.category_id = :category_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY transactions.date_of_creation DESC
        LIMIT :limit OFFSET :offset
    """)
    fun getPosByCategoryOfAllWallets(
        @Bind("category_id") categoryId: Int,
        @Bind("user_id") userId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
            JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.user_id = :user_id 
            AND transactions.amount < 0
            AND transactions.category_id = :category_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY transactions.date_of_creation DESC
        LIMIT :limit OFFSET :offset
    """)
    fun getNegByCategoryOfAllWallets(
        @Bind("category_id") categoryId: Int,
        @Bind("user_id") userId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?


    @SqlQuery("""
        SELECT categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password, SUM(transactions.amount) AS sum
        FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        GROUP BY categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password
    """)
    fun getBalanceByCategory(
        @Bind("wallet_id") walletId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
    ): List<CategoryBalance>?

    @SqlQuery("""
        SELECT categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password, SUM(transactions.amount) AS sum
        FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
        WHERE transactions.user_id = :user_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        GROUP BY categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password
    """)
    fun getBalanceByCategoryOfAllWallets(
        @Bind("user_id") userId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
    ): List<CategoryBalance>?


    @SqlQuery("""
        SELECT categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password, SUM(transactions.amount) AS sum
        FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.amount >=0 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        GROUP BY categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password
    """)
    fun getPositiveBalanceByCategory(
        @Bind("wallet_id") walletId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
    ): List<CategoryBalance>?

    @SqlQuery("""
        SELECT categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password, SUM(transactions.amount) AS sum
        FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
        WHERE transactions.user_id = :user_id 
            AND transactions.amount < 0 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        GROUP BY categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password
    """)
    fun getNegativeBalanceByCategoryOfAllWallets(
        @Bind("user_id") userId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
    ): List<CategoryBalance>?

    @SqlQuery("""
        SELECT categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password, SUM(transactions.amount) AS sum
        FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.amount < 0
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        GROUP BY categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password
    """)
    fun getNegativeBalanceByCategory(
        @Bind("wallet_id") walletId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
    ): List<CategoryBalance>?

    @SqlQuery("""
        SELECT categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password, SUM(transactions.amount) AS sum
        FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
        WHERE transactions.user_id = :user_id 
            AND transactions.amount >= 0
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        GROUP BY categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password
    """)
    fun getPositiveBalanceByCategoryOfAllWallets(
        @Bind("user_id") userId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
    ): List<CategoryBalance>?

    /** ----------------------------------- OverView --------------------------------   */

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.category_id = :category_id
        ORDER BY transactions.date_of_creation DESC
        LIMIT :limit OFFSET :offset
    """)
    fun getAllByCategory(
        @Bind("category_id") categoryId: Int,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?


    @SqlQuery("""
        SELECT categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password, SUM(transactions.amount) AS sum
            FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN MoneyMate.category categories ON transactions.category_id = categories.category_id
            GROUP BY categories.category_id, categories.category_name, users.user_id, users.username, users.email, users.password
    """)
    fun getAllBalanceByCategory(): List<CategoryBalance>?



    /** ----------------------------------- SW --------------------------------   */

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
            JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.user_id = :user_id
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        ORDER BY transactions.date_of_creation DESC
        LIMIT :limit OFFSET :offset

    """)
    fun getByUser(
        @Bind("wallet_id") walletId: Int,
        @Bind("user_id") userId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?

    @SqlQuery("""
        SELECT users.*, SUM(transactions.amount)
        FROM MoneyMate.transactions transactions
            JOIN MoneyMate.users users ON transactions.user_id =  users.user_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')        
        GROUP BY users.user_id
    """)
    fun getBalanceByUser(
        @Bind("wallet_id") walletId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
    ): List<UserBalance>?

    @SqlQuery("""
        SELECT users.*, SUM(transactions.amount) AS sum
        FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.amount < 0
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        GROUP BY users.user_id
    """)
    fun getNegativeBalanceByUser(
        @Bind("wallet_id") walletId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
    ): List<UserBalance>?

    @SqlQuery("""
        SELECT users.*, SUM(transactions.amount) AS sum
        FROM MoneyMate.transactions transactions
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
        WHERE transactions.wallet_id = :wallet_id 
            AND transactions.amount >= 0
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
        GROUP BY users.user_id
    """)
    fun getPositiveBalanceByUser(
        @Bind("wallet_id") walletId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,
    ): List<UserBalance>?

    @SqlQuery("""
        SELECT users.*, SUM(transactions.amount)
        FROM MoneyMate.transactions transactions
            JOIN MoneyMate.users users ON transactions.user_id =  users.user_id
        WHERE transactions.wallet_id = :wallet_id 
        GROUP BY users.user_id
    """)
    fun getBalanceByUserInSw(
        @Bind("wallet_id") walletId: Int,
    ): List<UserBalance>?

    @SqlQuery("""
        SELECT COUNT(usw.user_id)
        FROM MoneyMate.user_shared_wallet usw
        WHERE usw.wallet_id = :wallet_id
    """)
    fun getNumberOfMembers(
        @Bind("wallet_id") walletId: Int,
    ): Int


    /** ----------------------------------- Regular --------------------------------   */


    @SqlQuery("""
        SELECT *
        FROM MoneyMate.transactions transactions 
        JOIN Moneymate.users users ON transactions.user_id = users.user_id
        JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
        JOIN Moneymate.category category ON transactions.category_id = category.category_id
        LIMIT :limit OFFSET :offset
    """)
    fun getPeriodicalTransactions(
        @Bind("offset") offset: Int,
        @Bind("limit") limit: Int
    ): List<Transaction>?


    @SqlUpdate("""
        UPDATE MoneyMate.transactions
        SET periodical = :periodical
        WHERE transactions.transaction_id = :transaction_id
    """)
    fun updateTransactionFrequency(
        @Bind("transaction_id") transactionId: Int,
        @Bind("periodical") periodical: Int,
    )

    @SqlUpdate("""
        UPDATE MoneyMate.transactions
        SET amount = :amount
        WHERE transactions.transaction_id = :transaction_id
    """)
    fun updateTransactionAmount(
        @Bind("transaction_id") transactionId: Int,
        @Bind("amount") amount: Float,
    )

    @SqlUpdate("""
        UPDATE MoneyMate.transactions
        SET category_id = :new_category_id
        WHERE transactions.user_id = :user_id AND transactions.category_id = :old_category_id
    """)
    fun updateTransactionsCategories(
        @Bind("user_id") userId: Int,
        @Bind("old_category_id") oldCategoryId: Int,
        @Bind("new_category_id") newCategoryId: Int
    )

    @SqlUpdate("""
        DELETE FROM MoneyMate.transactions
        WHERE transactions.wallet_id = :wallet_id
        """)
    fun deleteTransactionsOfWallet(
        @Bind("wallet_id") walletId: Int,
    )
    @SqlQuery("""
        SELECT 
            SUM( CASE WHEN transactions.amount >=0 THEN transactions.amount ELSE 0 END) AS income_sum,
            SUM( CASE WHEN transactions.amount < 0 THEN transactions.amount ELSE 0 END) AS expense_sum
        FROM MoneyMate.transactions transactions
        WHERE transactions.wallet_id = :wallet_id
            AND transactions.date_of_creation >= :start_date::date
            AND transactions.date_of_creation < (:end_date::date + INTERVAL '1 day')
    """)
    fun getWalletBalance(
        @Bind("wallet_id")walletId: Int,
        @Bind("start_date") startDate: Date,
        @Bind("end_date") endDate: Date,

    ) : WalletBalance
}