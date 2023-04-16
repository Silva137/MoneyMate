package isel.pt.moneymate.repository.mappers

import isel.pt.moneymate.domain.Category
import isel.pt.moneymate.domain.Wallet
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.sql.SQLException

class CategoryMapper (private val userMapper: UserMapper)  : RowMapper<Category> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Category {
        val user = userMapper.map(rs, ctx)
        return Category(
            rs.getInt("category_id"),
            rs.getString("name"),
            user
        )
    }
}