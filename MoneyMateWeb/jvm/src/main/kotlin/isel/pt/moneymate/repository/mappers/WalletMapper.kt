package isel.pt.moneymate.repository.mappers

import isel.pt.moneymate.domain.Wallet
import org.jdbi.v3.core.statement.StatementContext
import org.jdbi.v3.core.mapper.RowMapper
import java.sql.ResultSet
import java.sql.SQLException


class WalletMapper(private val userMapper: UserMapper)  : RowMapper<Wallet> {

    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Wallet {
        val user = userMapper.map(rs, ctx)
        return Wallet(
            rs.getInt("id"),
            rs.getString("name"),
            user,
            rs.getDate("date_of_creation")
        )
    }
}