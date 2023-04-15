package isel.pt.moneymate.services

import isel.pt.moneymate.config.JwtService
import isel.pt.moneymate.controller.models.AuthenticationOutputModel
import isel.pt.moneymate.controller.models.LoginInputModel
import isel.pt.moneymate.controller.models.UserOutputModel
import isel.pt.moneymate.controller.models.UsersOutputModel
import isel.pt.moneymate.repository.UsersRepository
import isel.pt.moneymate.services.dtos.UserDTO
import isel.pt.moneymate.services.exceptions.NotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(rollbackFor = [Exception::class])
class UsersService(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService : JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(userDTO: UserDTO): AuthenticationOutputModel {
        val passwordHash = passwordEncoder.encode(userDTO.password)
        usersRepository.register(userDTO.username, userDTO.email, passwordHash)
        val user = usersRepository.getUserByEmail(userDTO.email) ?: throw Exception("User not found")
        val jwtToken = jwtService.generateToken(user)

        return AuthenticationOutputModel(jwtToken)
    }

    fun login(loginInput: LoginInputModel): AuthenticationOutputModel {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginInput.email, loginInput.password)
        )
        val user = usersRepository.getUserByEmail(loginInput.email) ?: throw Exception("User not found")
        val jwtToken = jwtService.generateToken(user)

        return AuthenticationOutputModel(jwtToken)
    }


    fun getUser(id: Int): UserOutputModel {
        val user = usersRepository.getUser(id) ?: throw NotFoundException("User with id:$id not found")
        return UserOutputModel(user._username, user.email)
    }

    fun getUsers(): UsersOutputModel {
        val users = usersRepository.getAllUsers() ?: throw NotFoundException("No users found")
        val usersInfo = users.map { UserOutputModel(it.username, it.email) }
        return UsersOutputModel(usersInfo)
    }

    fun updateUser(id: Int, username: String) : UserOutputModel{
        usersRepository.updateUsername(id, username)
        val editedUser = usersRepository.getUser(id) ?: throw NotFoundException("User with id:$id not found")
        return UserOutputModel(editedUser.username, editedUser.email)
    }

    /*fun getUserByToken(token: String): User {
        val user = jwtService.getUserFromToken(token) ?: throw AuthenticationException("Invalid token")
        return user
    }*/
}