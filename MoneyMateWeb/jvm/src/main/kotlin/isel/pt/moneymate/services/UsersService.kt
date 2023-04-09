package isel.pt.moneymate.services

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.repository.UsersRepository
import isel.pt.moneymate.services.dtos.RegisterInputDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(rollbackFor = [Exception::class])
class UsersService(private val usersRepository: UsersRepository) {

    fun register(registerInputDTO: RegisterInputDTO): User {
        usersRepository.register(registerInputDTO.username, registerInputDTO.email, registerInputDTO.password)
        return User(10, registerInputDTO.username, registerInputDTO.email, registerInputDTO.password)
    }


    fun getUser(id: Int): User {
        val user = usersRepository.getUser(id) ?: throw Exception("User not found")
        return user  // return userDTO(user) ??
    }

    fun getUsers(): List<User> {
        return usersRepository.getAllUsers()
    }
}