package isel.pt.moneymate.repository.mappers

import isel.pt.moneymate.domain.*
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.sql.SQLException

class RegularTransactionMapper (
    private val transactionMapper: TransactionMapper,

)  : RowMapper<RegularTransaction> {

    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): RegularTransaction {
        val transaction = transactionMapper.map(rs, ctx)
        return RegularTransaction(
            rs.getInt("regular_transaction_id"),
            rs.getString("frequency"),
            transaction
        )
    }
}


