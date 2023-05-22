package isel.pt.moneymate

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import isel.pt.moneymate.http.models.users.CreateUserDTO
import isel.pt.moneymate.http.models.users.UserDTO
import isel.pt.moneymate.http.models.wallets.CreateWalletDTO
import isel.pt.moneymate.http.models.wallets.WalletDTO
import isel.pt.moneymate.http.utils.Uris
import isel.pt.moneymate.services.UsersService
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class WalletControllerTests {

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
    fun `create a wallet`() {
        val walletData = CreateWalletDTO("Test Wallet")
        val userDTO = UserDTO(99, "testUser", "testeemail@gmail.com")
        val newWallet = WalletDTO(99, "Test Wallet", userDTO, Date())

        val performPost = mockMvc.post(Uris.Wallets.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(walletData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        val result = performPost
            .andExpect {
                status { isCreated() }
                jsonPath<String>("$.name", Matchers.equalTo(newWallet.name))
                jsonPath("$.user.username", Matchers.equalTo(newWallet.user.username))
            }
        val response = result.andReturn().response.contentAsString

        val gson = Gson()
        val wallet = gson.fromJson(response, WalletDTO::class.java)

        mockMvc.delete(Uris.Wallets.DELETE_BY_ID, wallet.id) {
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
    }

    @Test
    fun `get wallets of user`() {
        val walletData = CreateWalletDTO("Test Wallet")
        val performPost = mockMvc.post(Uris.Wallets.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(walletData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        val gson = Gson()
        val wallet = gson.fromJson(performPost.andReturn().response.contentAsString, WalletDTO::class.java)


        val performGet = mockMvc.get(Uris.Wallets.GET_WALLETS_OF_USER) {
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }

        performGet.andExpect {
            status { isOk() }
            jsonPath("$.totalCount", Matchers.equalTo(1))
        }
        mockMvc.delete(Uris.Wallets.DELETE_BY_ID, wallet.id) {
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
    }

    @Test
    fun `update a wallet`() {
        val walletData = CreateWalletDTO("Test Wallet")
        val updateWallet = CreateWalletDTO("Updated Wallet")
        val performPost = mockMvc.post(Uris.Wallets.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(walletData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        val gson = Gson()
        val wallet = gson.fromJson(performPost.andReturn().response.contentAsString, WalletDTO::class.java)

        val performPatch = mockMvc.patch(Uris.Wallets.UPDATE_NAME, wallet.id) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateWallet)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }

        performPatch.andExpect {
            status { isOk() }
            jsonPath("$.name", Matchers.equalTo("Updated Wallet"))
        }
        mockMvc.delete(Uris.Wallets.DELETE_BY_ID, wallet.id) {
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
    }
    @Test
    fun missingAuthorizationHeader(){
        val walletData = CreateWalletDTO("Test Wallet")
        val performPost = mockMvc.post(Uris.Wallets.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(walletData)

        }
        performPost
            .andExpect {
                status { isUnauthorized() }
            }
    }

    @Test
    fun createWalletWithoutName(){
        val walletsData = CreateWalletDTO("")
        val performPost = mockMvc.post(Uris.Wallets.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(walletsData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        performPost
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun updateWalletNotFound(){

        val walletData = CreateWalletDTO("Test Wallet")
        val updateWallet = CreateWalletDTO("Updated Wallet")
        val performPost = mockMvc.post(Uris.Wallets.CREATE) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(walletData)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
        val gson = Gson()
        val wallet = gson.fromJson(performPost.andReturn().response.contentAsString, WalletDTO::class.java)

        val performPatch = mockMvc.patch(Uris.Wallets.UPDATE_NAME, 999) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateWallet)
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }

        performPatch
            .andExpect {
                status { isNotFound() }
            }
        mockMvc.delete(Uris.Wallets.DELETE_BY_ID, wallet.id) {
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $VALID_TOKEN")
        }
    }

}

