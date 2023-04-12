package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.Category
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository {

        @SqlUpdate("INSERT INTO MoneyMate.category (name, user_id) VALUES (:name, :userId)")
        @GetGeneratedKeys("id")
        fun createCategory(@Bind("name") name: String, @Bind("userId") userId: Int) : Int
        @SqlQuery("""
            SELECT c.*, u.*
            FROM MoneyMate.category c 
            JOIN MoneyMate.users u ON c.user_id = u.id 
         """)
        fun getCategories(): List<Category>
        @SqlQuery("SELECT c.* , u.* FROM MoneyMate.category c  JOIN MoneyMate.users u ON c.user_id = u.id WHERE c.id = :id ")
        fun getCategoryById(@Bind("id") categoryId: Int): Category?
        @SqlUpdate("UPDATE MoneyMate.category SET name = :name WHERE id = :id ")
        fun updateCategoryName(@Bind("name") newName: String, @Bind("id") categoryId: Int)
        @SqlUpdate("DELETE FROM MoneyMate.category WHERE id = :id")
        fun deleteCategoryById(@Bind("id") categoryId: Int)

}