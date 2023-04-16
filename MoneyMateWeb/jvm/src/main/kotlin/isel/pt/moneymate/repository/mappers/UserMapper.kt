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
