package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.User
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository {
    @SqlUpdate("INSERT INTO MoneyMate.users (username, email, password) VALUES (:username, :email, :password)")
    fun register(@Bind("username") name: String, @Bind("email") email: String, @Bind("password") password: String)

    @SqlQuery("SELECT * FROM MoneyMate.users WHERE id = :id")
    fun getUser(@Bind("id") id: Int): User

    @SqlQuery("SELECT * FROM MoneyMate.users")
    fun getAllUsers(): List<User>
}