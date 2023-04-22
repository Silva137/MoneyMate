package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.Token
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.SqlBatch
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface TokensRepository {

    @SqlQuery("""
    SELECT t.*, u.* FROM MoneyMate.tokens t
    INNER JOIN MoneyMate.users u ON u.user_id = t.userid
    WHERE t.token = :token 
    """)
    fun findByToken(@Bind("token") token: String): Token?

    @SqlUpdate("INSERT INTO MoneyMate.tokens (token, revoked, expired, userId) VALUES (:token, :revoked, :expired, :userId)")
    fun save(@Bind("token") token: String, @Bind("revoked") revoked: Boolean, @Bind("expired") expired: Boolean, @Bind("userId") userId: Int)

    @SqlBatch("UPDATE  MoneyMate.tokens SET token = :token.token, revoked = true, expired = true, userId = :token.user.id WHERE token_id = :token.id")
    fun revokeTokens(@BindBean("token") tokens: List<Token>)


    @SqlQuery("""
    SELECT t.*, u.* FROM MoneyMate.tokens t
    INNER JOIN MoneyMate.users u ON u.user_id = t.userid
    WHERE t.userid = :userId AND t.expired = false AND t.revoked = false
    """)
    fun findAllValidTokensByUser(@Bind("userId") userId: Int): List<Token>

    @SqlUpdate("DELETE FROM MoneyMate.tokens WHERE userId = :userId")
    fun deleteUserTokens(@Bind("userId") userId: Int)

}
