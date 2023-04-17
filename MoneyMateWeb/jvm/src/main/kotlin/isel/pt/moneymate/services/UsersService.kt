package isel.pt.moneymate.services

import com.fasterxml.jackson.databind.ObjectMapper
import isel.pt.moneymate.config.JwtService
import isel.pt.moneymate.domain.Token
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.exceptions.AlreadyExistsException
import isel.pt.moneymate.exceptions.InvalidLoginException
import isel.pt.moneymate.exceptions.NotFoundException
import isel.pt.moneymate.http.models.users.*
import isel.pt.moneymate.repository.TokensRepository
import isel.pt.moneymate.repository.UsersRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(rollbackFor = [Exception::class])
class UsersService(
    private val usersRepository: UsersRepository,
    private val tokensRepository: TokensRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService : JwtService,
    private val authenticationManager: AuthenticationManager
) {

    //change model to dto
    fun register(registerInputDTO: CreateUserDTO): AuthenticationOutDTO {
        if(usersRepository.getUserByUsername(registerInputDTO.username) != null)
            throw AlreadyExistsException("User with username ${registerInputDTO.username} already exists")

        if(usersRepository.getUserByEmail(registerInputDTO.email) != null)
            throw AlreadyExistsException("User with email ${registerInputDTO.email} already exists")

        val passwordHash = passwordEncoder.encode(registerInputDTO.password)
        usersRepository.register(registerInputDTO.username, registerInputDTO.email, passwordHash)

        val savedUser = usersRepository.getUserByEmail(registerInputDTO.email)
            ?: throw Exception("User with email ${registerInputDTO.email} already exists")

        val accessToken = jwtService.generateToken(savedUser)
        val refreshToken = jwtService.generateRefreshToken(savedUser)
        saveUserToken(savedUser, accessToken);

        return AuthenticationOutDTO(accessToken, refreshToken)
    }

    fun login(loginInput: LoginUserDTO): AuthenticationOutDTO {
        try{
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginInput.email, loginInput.password)
            )
        } catch (ex: BadCredentialsException) {
            throw InvalidLoginException(ex.message ?: "Invalid login credentials")
        }
        val user = usersRepository.getUserByEmail(loginInput.email) ?: throw Exception("User not found")
        val accessToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        revokeAllUserTokens(user)
        saveUserToken(user, accessToken)

        return AuthenticationOutDTO(accessToken, refreshToken)
    }

    fun refreshToken(request: HttpServletRequest?, response: HttpServletResponse?) {
        val authHeader = request?.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }
        val refreshToken = authHeader.substring(7)
        val userEmail = jwtService.extractUsername(refreshToken)
        if (userEmail != null) {
            val user = usersRepository.getUserByEmail(userEmail) ?: throw NotFoundException("User $userEmail not found")
            if (jwtService.isTokenValid(refreshToken, user)) {
                val accessToken = jwtService.generateToken(user)
                revokeAllUserTokens(user)
                saveUserToken(user, accessToken)
                val authResponse = AuthenticationOutDTO(accessToken, refreshToken)
                ObjectMapper().writeValue(response?.outputStream, authResponse)
            }
        }
    }

    fun getUser(id: Int): GetUserDTO {
        val user = usersRepository.getUser(id) ?: throw NotFoundException("User with id:$id not found")
        return GetUserDTO(user._username, user.email)
    }

    fun getUsers(): GetUsersDTO {
        val users = usersRepository.getAllUsers() ?: throw NotFoundException("No users found")
        val usersInfo = users.map { GetUserDTO(it.username, it.email) }
        return GetUsersDTO(usersInfo)
    }

    fun updateUser(id: Int, username: String) : GetUserDTO {
        usersRepository.updateUsername(id, username)
        val editedUser = usersRepository.getUser(id) ?: throw NotFoundException("User with id:$id not found")
        return GetUserDTO(editedUser.username, editedUser.email)
    }

    private fun saveUserToken(user: User, jwtToken: String) {
        val token = Token(user = user, token = jwtToken, expired = false, revoked = false)
        tokensRepository.save(token.token, token.expired, token.revoked, token.user.id)
    }

    private fun revokeAllUserTokens(user: User) {
        val validUserTokens = tokensRepository.findAllValidTokensByUser(user.id)
        if (validUserTokens.isEmpty()) return
        tokensRepository.revokeTokens(validUserTokens)
    }
}