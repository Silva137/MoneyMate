package isel.pt.moneymate.repository


import isel.pt.moneymate.repository.utils.InitValues
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersRepositoryTests {
    // FIRST RUN CREATE-SCHEMA

    @Autowired
    private lateinit var usersRepository: UsersRepository

    val initialUserName = InitValues.InitUser.initialUserName
    val updatedUserName = InitValues.InitUser.updatedUserName
    val userEmail = InitValues.InitUser.userEmail
    val userPasswordHash = InitValues.InitUser.userPasswordHash

    @BeforeAll
    fun init(){
        // Register a new user
        usersRepository.register(initialUserName, userEmail, userPasswordHash)
    }

    @Test
    fun testRegisterAndGetUser() {
        // Get the registered user by id
        val user = usersRepository.getUserById(1)
        assertNotNull(user)
        assertEquals(userEmail, user!!.email)
        assertEquals(userPasswordHash, user.passwordHash)
    }

    @Test
    fun testGetAllUsers() {
        usersRepository.register("Jane", "jane@example.com", "password")

        // Get all users (should now contain users)
        val allUsers = usersRepository.getAllUsers()
        assertEquals(2, allUsers!!.size)
    }

    @Test
    fun testGetUserByEmail() {
        // Register a new user
        // Get the registered user by email
        val user = usersRepository.getUserByEmail(userEmail)
        assertNotNull(user)
        assertEquals(initialUserName, user!!._username)
        assertEquals(userEmail, user.email)
        assertEquals(userPasswordHash, user.passwordHash)
    }

    @Test
    fun testGetUserByUsername() {
        // Get the registered user by username
        var user = usersRepository.getUserByUsername(initialUserName)
        val user1 = usersRepository.getUserByUsername(updatedUserName)
        if(user == null)
            user = user1
        assertNotNull(user)
        assertEquals(userEmail, user!!.email)
        assertEquals(userPasswordHash, user.passwordHash)
    }

    @Test
    fun testUpdateUsername() {
        // Update the user's username
        val newUsername = updatedUserName
        usersRepository.updateUsername(1, newUsername)

        // Get the updated user
        val user = usersRepository.getUserById(1)
        assertNotNull(user)
        assertEquals(newUsername, user!!._username)
    }
}