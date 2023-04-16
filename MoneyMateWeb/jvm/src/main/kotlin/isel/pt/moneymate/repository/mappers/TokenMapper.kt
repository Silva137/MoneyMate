package isel.pt.moneymate.repository.mappers

import isel.pt.moneymate.domain.Category
import isel.pt.moneymate.domain.Token
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.sql.SQLException

class TokenMapper (private val userMapper: UserMapper)  : RowMapper<Token> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Token {
        val user = userMapper.map(rs, ctx)
        return Token(
            id = rs.getInt("token_id"),
            token = rs.getString("token"),
            revoked = rs.getBoolean("revoked"),
            expired = rs.getBoolean("expired"),
            user = user
        )
    }
}