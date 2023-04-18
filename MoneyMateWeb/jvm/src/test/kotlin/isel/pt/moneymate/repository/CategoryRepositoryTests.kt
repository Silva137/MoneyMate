package isel.pt.moneymate.repository


import isel.pt.moneymate.repository.utils.InitValues
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryRepositoryTests {
    // FIRST RUN CREATE-SCHEMA

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    val userId = InitValues.InitUser.initialUserId

    @BeforeAll
    fun init(){
        // Register new Categories
        categoryRepository.createCategory("Shopping", userId)


    }

    @Test
    fun test() {
        //TODO()
    }

}