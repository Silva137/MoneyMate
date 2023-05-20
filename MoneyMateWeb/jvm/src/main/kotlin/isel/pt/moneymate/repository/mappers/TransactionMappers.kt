package isel.pt.moneymate.repository.mappers

import isel.pt.moneymate.domain.CategoryBalance
import isel.pt.moneymate.domain.WalletBalance
import isel.pt.moneymate.domain.Transaction
import isel.pt.moneymate.domain.UserBalance
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.sql.SQLException

class TransactionMapper (
    private val userMapper: UserMapper,
    private val categoryMapper: CategoryMapper,
    private val walletMapper: WalletMapper
)  : RowMapper<Transaction> {

    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Transaction {
        val user = userMapper.map(rs, ctx)
        val category = categoryMapper.map(rs, ctx)
        val wallet = walletMapper.map(rs, ctx)
        return Transaction(
            rs.getInt("transaction_id"),
            rs.getString("title"),
            rs.getFloat("amount"),
            user,
            wallet,
            category,
            rs.getTimestamp("date_of_creation").toLocalDateTime(),
            rs.getInt("periodical"),
        )
    }
}

class WalletBalanceMapper: RowMapper<WalletBalance> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): WalletBalance {
        return WalletBalance(
            rs.getInt("income_sum"),
            rs.getInt("expense_sum")
        )
    }
}

class CategoryBalanceMapper (private val categoryMapper: CategoryMapper): RowMapper<CategoryBalance> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): CategoryBalance {
        val category = categoryMapper.map(rs, ctx)
        return CategoryBalance(
            category,
            rs.getInt("sum")
        )
    }
}

class UserBalanceMapper (private val userMapper: UserMapper): RowMapper<UserBalance> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): UserBalance {
        val user = userMapper.map(rs, ctx)
        return UserBalance(
            user,
            rs.getInt("sum")
        )
    }
}

