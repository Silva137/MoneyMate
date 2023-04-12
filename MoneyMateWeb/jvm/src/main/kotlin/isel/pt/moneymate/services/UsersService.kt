package isel.pt.moneymate.services

import isel.pt.moneymate.config.JwtService
import isel.pt.moneymate.controller.models.LoginInputModel
import isel.pt.moneymate.controller.models.AuthenticationOutputModel
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.repository.UsersRepository
import isel.pt.moneymate.services.dtos.RegisterInputDTO
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

    fun register(registerInputDTO: RegisterInputDTO): AuthenticationOutputModel {
        val passwordHash = passwordEncoder.encode(registerInputDTO.password)
        usersRepository.register(registerInputDTO.username, registerInputDTO.email, passwordHash)
        val user = usersRepository.getUserByEmail(registerInputDTO.email) ?: throw Exception("User not found")
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

    fun getUser(id: Int): User {
        val user = usersRepository.getUser(id)
        return user
    }

    fun getUsers(): List<User> {
        return usersRepository.getAllUsers()
    }


}