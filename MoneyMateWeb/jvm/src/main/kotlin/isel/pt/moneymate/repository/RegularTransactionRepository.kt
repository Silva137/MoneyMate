package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.*
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import java.sql.Date
import java.time.LocalDateTime

@Repository
interface RegularTransactionRepository {



    @SqlQuery("""
        SELECT *
        FROM MoneyMate.regular_transactions rt 
            JOIN MoneyMate.transactions transactions ON rt.transaction_info_id = transactions.transaction_id
            JOIN Moneymate.users users ON transactions.user_id = users.user_id
            JOIN Moneymate.wallet wallet ON transactions.wallet_id = wallet.wallet_id
            JOIN Moneymate.category category ON transactions.category_id = category.category_id
        WHERE transactions.transaction_id = :transaction_id
    """)
    fun getTransactionById(@Bind("transaction_id") transactionId: Int): RegularTransaction?


    @SqlUpdate("""
        INSERT INTO MoneyMate.regular_transactions(frequency, transaction_info_id) 
        VALUES (:frequency, :transaction_id)
    """)
    @GetGeneratedKeys("regular_transaction_id")
    fun createRegularTransaction(
        @Bind("frequency") frequency: String,
        @Bind("transaction_id") transactionId: Int,
    ): Int


    @SqlUpdate("""
        UPDATE MoneyMate.regular_transactions
        SET frequency = :frequency
        WHERE regular_transaction_id = :regular_transaction_id
    """)
    fun updateTransaction(
        @Bind("regular_transaction_id") regularTransactionId: Int,
        @Bind("frequency") frequency: String,
    )

    @SqlQuery("""
        SELECT *
        FROM MoneyMate.regular_transactions rt
            JOIN Moneymate.transactions t ON rt.transaction_info_id = t.transaction_id
            JOIN Moneymate.users u ON u.user_id = t.user_id
            JOIN Moneymate.wallet wallet ON t.wallet_id = wallet.wallet_id
            JOIN Moneymate.category category ON t.category_id = category.category_id
        WHERE t.user_id = :user_id
    """)
    fun getAll(
        @Bind("user_id") userId: Int,
    ): List<RegularTransaction>?

}