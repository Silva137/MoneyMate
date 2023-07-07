package isel.pt.moneymate.repository.mappers

import isel.pt.moneymate.domain.*
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.sql.SQLException

class InviteMapper (
    private val userMapper1: UserMapperWithParams,
    private val userMapper2: UserMapperWithParams,
    private val walletMapper: WalletMapper
)  : RowMapper<Invite> {

    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): Invite {
        val userReceiver = userMapper1.map(rs, ctx)
        val userSender = userMapper2.map(rs, ctx)
        val wallet = walletMapper.map(rs, ctx)
        val state = InviteState.valueOf(rs.getString("state"))

        return Invite(
            rs.getInt("invite_id"),
            userSender,
            userReceiver,
            wallet,
            state,
            rs.getBoolean("on_finished"),
            rs.getDate("date_of_creation")
        )
    }
}

