package isel.pt.moneymate.repository.mappers

import isel.pt.moneymate.domain.User
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.sql.SQLException

class UserMapper : RowMapper<User> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): User {
        return User(
            rs.getInt("user_id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
        )
    }
}

class UserMapperWithParams(
    private val userIdColumn: String,
    private val usernameColumn: String,
    private val emailColumn: String,
    private val passwordColumn: String
) : RowMapper<User> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): User {
        val userId = rs.getInt(userIdColumn)
        val username = rs.getString(usernameColumn)
        val email = rs.getString(emailColumn)
        val password = rs.getString(passwordColumn)

        return User(userId, username, email, password)
    }
}
