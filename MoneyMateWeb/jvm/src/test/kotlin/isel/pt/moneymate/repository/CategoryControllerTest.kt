package isel.pt.moneymate.repository


import com.fasterxml.jackson.databind.ObjectMapper
import isel.pt.moneymate.config.JwtService
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.http.models.categories.CategoryDTO
import isel.pt.moneymate.http.models.categories.CreateCategoryDTO
import isel.pt.moneymate.http.models.users.CreateUserDTO
import isel.pt.moneymate.http.models.users.UserDTO
import isel.pt.moneymate.http.utils.Uris
import isel.pt.moneymate.services.UsersService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders


@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var usersService: UsersService

    private lateinit var VALID_TOKEN: String

    @BeforeEach
    fun setUp() {
        VALID_TOKEN = usersService.register(CreateUserDTO("testUser", "testeemail@gmail.com", "password")).accessToken
    }

    @AfterEach
    fun end() {
        usersService.deleteUser(1)
    }


    @Test
    fun `create a category`() {
        val categoryData = CreateCategoryDTO("Test Category")
        val userDTO = UserDTO("testUser","testeemail@gmail.com")
        val newCategory = CategoryDTO("Test Category", userDTO)

        val performPost = mockMvc.post(Uris.Category.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(categoryData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }

        performPost
            .andExpect {
                status { isCreated() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(newCategory))
                }
            }
    }
}

