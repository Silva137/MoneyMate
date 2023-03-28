package isel.pt.moneymate.services

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.repository.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(rollbackFor = [Exception::class])
class UsersService(private val usersRepository: UsersRepository) {

    fun getUser(id: Int): User {
        val user = usersRepository.getUser(id)
        return user  // return userDTO(user) ??
    }

    fun getUsers(): List<User> {
        return usersRepository.getAllUsers()
    }
}