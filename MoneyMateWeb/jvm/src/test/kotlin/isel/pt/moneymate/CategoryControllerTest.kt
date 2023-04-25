package isel.pt.moneymate


import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import isel.pt.moneymate.http.models.categories.CategoryDTO
import isel.pt.moneymate.http.models.categories.CreateCategoryDTO
import isel.pt.moneymate.http.models.categories.UpdateCategoryDTO
import isel.pt.moneymate.http.models.users.CreateUserDTO
import isel.pt.moneymate.http.models.users.UserDTO
import isel.pt.moneymate.http.utils.Uris
import isel.pt.moneymate.services.UsersService
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.*


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
        val user = usersService.getUserByEmail("testeemail@gmail.com")
        usersService.deleteUser(user.id)
    }


    @Test
    fun `create a category`() {
        val categoryData = CreateCategoryDTO("Test Category")
        val userDTO = UserDTO(99,"testUser","testeemail@gmail.com")
        val newCategory = CategoryDTO(99,"Test Category", userDTO)

        val performPost = mockMvc.post(Uris.Category.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(categoryData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        val result = performPost
            .andExpect{
                status { isCreated() }
                jsonPath<String>("$.name", equalTo(newCategory.name))
                jsonPath("$.user.username", equalTo(newCategory.user?.username))
            }
        val response = result.andReturn().response.contentAsString

        val gson = Gson()
        val category = gson.fromJson(response,CategoryDTO::class.java)

        mockMvc.delete(Uris.Category.DELETE_BY_ID,category.id){
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
    }

    @Test
    fun getCategories(){
        val performGet = mockMvc.get(Uris.Category.GET_CATEGORIES){
            contentType =  MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION,"Bearer $VALID_TOKEN")
        }

        performGet
            .andExpect {
                status { isOk() }
                jsonPath("$.totalCount", equalTo(10))
            }
    }

    @Test
    fun updateCategory(){
        val categoryData = CreateCategoryDTO("Test Category")
        val updatedData = UpdateCategoryDTO("Updated Category")
        val performPost = mockMvc.post(Uris.Category.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(categoryData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        val gson = Gson()
        val category = gson.fromJson(performPost.andReturn().response.contentAsString,CategoryDTO::class.java)

        val performPatch = mockMvc.patch(Uris.Category.UPDATE,category.id){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }

        performPatch
            .andExpect {
                status { isOk() }
                jsonPath("$.name", equalTo("Updated Category"))
            }
        mockMvc.delete(Uris.Category.DELETE_BY_ID,category.id){
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }

    }

    @Test
    fun missingAuthorizationHeader(){
        val categoryData = CreateCategoryDTO("Test Category")
        val performPost = mockMvc.post(Uris.Category.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(categoryData)

        }

        performPost
            .andExpect {
                status { isUnauthorized() }
            }
    }
    @Test
    fun createCategoryWithoutName(){
        val categoryData = CreateCategoryDTO("")
        val performPost = mockMvc.post(Uris.Category.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(categoryData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        performPost
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun updateCategoryNotFound(){
        val categoryData = CreateCategoryDTO("Test Category")
        val updatedData = UpdateCategoryDTO("Updated Category")
        val performPost = mockMvc.post(Uris.Category.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(categoryData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        val gson = Gson()
        val category = gson.fromJson(performPost.andReturn().response.contentAsString,CategoryDTO::class.java)


        val performPatch = mockMvc.patch(Uris.Category.UPDATE,999){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        performPatch
            .andExpect {
                status { isNotFound() }
            }
        mockMvc.delete(Uris.Category.DELETE_BY_ID,category.id){
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
    }
    @Test
    fun updateCategoryWithoutName(){
        val categoryData = CreateCategoryDTO("Test Category")
        val performPost = mockMvc.post(Uris.Category.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(categoryData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        val gson = Gson()
        val category = gson.fromJson(performPost.andReturn().response.contentAsString,CategoryDTO::class.java)
        val performPatch = mockMvc.patch(Uris.Category.UPDATE,category.id){
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        performPatch
            .andExpect {
                status { isBadRequest() }
            }
        mockMvc.delete(Uris.Category.DELETE_BY_ID,category.id){
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
    }

}

