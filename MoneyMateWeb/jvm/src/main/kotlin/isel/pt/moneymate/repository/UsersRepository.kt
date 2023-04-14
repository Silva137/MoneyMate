package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.User
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository {
    @SqlUpdate("INSERT INTO MoneyMate.users (username, email, password) VALUES (:username, :email, :password)")
    fun register(@Bind("username") name: String, @Bind("email") email: String, @Bind("password") passwordHash: String)

    @SqlQuery("SELECT * FROM MoneyMate.users WHERE id = :id")
    fun getUser(@Bind("id") id: Int): User?

    @SqlQuery("SELECT * FROM MoneyMate.users")
    fun getAllUsers(): List<User>?

    @SqlQuery("SELECT * FROM MoneyMate.users WHERE email = :email")
    fun getUserByEmail(@Bind("email") email: String): User?

    @SqlQuery("SELECT * FROM MoneyMate.users WHERE username = :username")
    fun getUserByUsername(@Bind("username") username: String): User?

    @SqlUpdate("UPDATE MoneyMate.users SET username = :newUsername WHERE id = :id")
    fun updateUsername(@Bind("id") id: Int, @Bind("newUsername") newUsername: String)

}