package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.User
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository {
    @SqlUpdate("INSERT INTO MoneyMate.users (username, email, password) VALUES (:username, :email, :password)")
    @GetGeneratedKeys("user_id")

    fun register(@Bind("username") name: String, @Bind("email") email: String, @Bind("password") passwordHash: String):Int

    @SqlQuery("SELECT * FROM MoneyMate.users WHERE user_id = :id")
    fun getUserById(@Bind("id") id: Int): User?

    @SqlQuery("SELECT * FROM MoneyMate.users LIMIT :limit OFFSET :offset")
    fun getAllUsers(@Bind("offset") offset: Int, @Bind("limit") limit: Int): List<User>?

    @SqlQuery("SELECT * FROM MoneyMate.users WHERE email = :email")
    fun getUserByEmail(@Bind("email") email: String): User?

    @SqlQuery("SELECT * FROM MoneyMate.users WHERE username = :username")
    fun getUserByUsername(@Bind("username") username: String): User?

    @SqlUpdate("UPDATE MoneyMate.users SET username = :newUsername WHERE user_id = :id")
    fun updateUsername(@Bind("id") id: Int, @Bind("newUsername") newUsername: String)

}