package isel.pt.moneymate.services

import isel.pt.moneymate.config.JwtService
import isel.pt.moneymate.domain.Token
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.exceptions.*
import isel.pt.moneymate.http.models.users.*
import isel.pt.moneymate.repository.TokensRepository
import isel.pt.moneymate.repository.UsersRepository
import isel.pt.moneymate.repository.WalletRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationProvider
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
    private val walletRepository: WalletRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService : JwtService,
    private val authenticationProvider: AuthenticationProvider
) {

    fun register(registerInputDTO: CreateUserDTO): AuthenticationOutDTO {
        if (usersRepository.getUserByUsername(registerInputDTO.username) != null)
            throw AlreadyExistsException("User with username ${registerInputDTO.username} already exists")

        if (usersRepository.getUserByEmail(registerInputDTO.email) != null)
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
        try {
            authenticationProvider.authenticate(
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

    fun refreshToken(request: HttpServletRequest?, response: HttpServletResponse?, refreshToken: String): AuthenticationOutDTO {
        val userEmail = jwtService.extractUsername(refreshToken)
        if (userEmail != null) {
            val user = usersRepository.getUserByEmail(userEmail) ?: throw NotFoundException("User $userEmail not found")
            if (jwtService.isTokenValid(refreshToken, user)) {
                val accessToken = jwtService.generateToken(user)
                revokeAllUserTokens(user)
                saveUserToken(user, accessToken)
                return AuthenticationOutDTO(accessToken, refreshToken)
            }
        }
        throw NotFoundException("Error extract username from refresh-token")
    }

    fun getUserById(id: Int): UserDTO {
        val user = usersRepository.getUserById(id)
            ?: throw NotFoundException("User with id:$id not found")
        return user.toDTO()
    }

    fun getUserByEmail(email: String): UserDTO {
        val user = usersRepository.getUserByEmail(email)
            ?: throw NotFoundException("User with email:$email not found")
        return user.toDTO()
    }

    fun getUsers(offset: Int, limit: Int): UsersDTO {
        val users = usersRepository.getAllUsers(offset, limit)
            ?: throw NotFoundException("No users found")
        return users.toDTO()
    }

    fun updateUser(userId: Int, username: String): UserDTO {
        usersRepository.updateUsername(userId, username)
        val editedUser = usersRepository.getUserById(userId)
            ?: throw NotFoundException("User with id:$userId not found")
        return editedUser.toDTO()
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

    fun deleteUser(userId: Int) {
        tokensRepository.deleteUserTokens(userId)
        usersRepository.deleteUser(userId)
    }

    fun verifyUserOnWallet(userId: Int, walletId: Int) {
        val userOfWallet = walletRepository.getUserOfPW(walletId)
            ?: throw NotFoundException("Wallet with id $walletId not found")

        if(userOfWallet != userId)
            throw UnauthorizedException("User does not have permission to perform this action")
    }
}