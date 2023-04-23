package isel.pt.moneymate.repository.mappers

import isel.pt.moneymate.controller.models.CategoriesBalance
import isel.pt.moneymate.controller.models.UserSumsOutDto
import isel.pt.moneymate.controller.models.WalletBalanceDTO
import isel.pt.moneymate.domain.Transaction
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

class WalletBalanceDtoMapper: RowMapper<WalletBalanceDTO> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): WalletBalanceDTO {
        return WalletBalanceDTO(
            rs.getInt("income_sum"),
            rs.getInt("expense_sum")
        )
    }
}

class CategorySumsDtoMapper (private val categoryMapper: CategoryMapper): RowMapper<CategoriesBalance> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): CategoriesBalance {
        val category = categoryMapper.map(rs, ctx)
        return CategoriesBalance(category.toDTO(), rs.getFloat("sum"))
    }
}

class UserSumsDtoMapper (private val userMapper: UserMapper): RowMapper<UserSumsOutDto> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): UserSumsOutDto {
        val user = userMapper.map(rs, ctx)
        return UserSumsOutDto(user.toDTO(), rs.getInt("sum"))
    }
}

