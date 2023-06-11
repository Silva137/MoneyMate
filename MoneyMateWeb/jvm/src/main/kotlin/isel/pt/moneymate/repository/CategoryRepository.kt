package isel.pt.moneymate.repository

import isel.pt.moneymate.domain.Category
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository {
        @SqlQuery(
            """
            SELECT c.user_id
            FROM MoneyMate.category c 
            WHERE c.category_id = :category_id
            """
        )
        fun getUserOfCategory(
            @Bind("category_id") categoryId: Int
        ): Int?

        @SqlUpdate("INSERT INTO MoneyMate.category (category_name, user_id) VALUES (:name, :userId)")
        @GetGeneratedKeys("category_id")
        fun createCategory(@Bind("name") name: String, @Bind("userId") userId: Int) : Int

        @SqlQuery("""
            SELECT c.*, u.*
            FROM MoneyMate.category c
            JOIN MoneyMate.users u ON c.user_id = u.user_id
            WHERE c.user_id = :user_id
            LIMIT :limit OFFSET :offset
         """)
        fun getCategories(
            @Bind("user_id") userId: Int,
            @Bind("offset") offset: Int,
            @Bind("limit") limit: Int
        ): List<Category>?

    @SqlQuery("""
            SELECT c.*, u.*
            FROM MoneyMate.category c
            JOIN MoneyMate.users u ON c.user_id = u.user_id
            WHERE c.user_id = 0
         """)
    fun getSystemCategories(): List<Category>?

        @SqlQuery("SELECT c.* , u.* FROM MoneyMate.category c  JOIN MoneyMate.users u ON c.user_id = u.user_id WHERE c.category_id = :id ")
        fun getCategoryById(@Bind("id") categoryId: Int): Category?

        @SqlUpdate("UPDATE MoneyMate.category SET category_name = :name WHERE category_id = :id ")
        fun updateCategoryName(@Bind("name") newName: String, @Bind("id") categoryId: Int)

        @SqlUpdate("DELETE FROM MoneyMate.category WHERE category_id = :id")
        fun deleteCategoryById(@Bind("id") categoryId: Int)


}