package isel.pt.moneymate.controller.pipeline

import isel.pt.moneymate.domain.User
import isel.pt.moneymate.services.UsersService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/*
@Component
class AuthorizationHeaderProcessor(val usersService: UsersService) {

    fun process(authorizationValue: String?): User? {
        if (authorizationValue == null) return null

        val parts = authorizationValue.trim().split(" ")
        if (parts.size != 2) return null
        if (parts[0].lowercase() != SCHEME) return null

        return usersService.getUserByToken(parts[1])
    }

    companion object {
        const val SCHEME = "bearer"
    }
}*/
