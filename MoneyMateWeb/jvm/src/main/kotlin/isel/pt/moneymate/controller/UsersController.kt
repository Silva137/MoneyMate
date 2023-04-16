package isel.pt.moneymate.controller

import isel.pt.moneymate.controller.models.LoginInputModel
import isel.pt.moneymate.controller.models.RegisterInputModel
import isel.pt.moneymate.controller.models.UserEditInputModel
import isel.pt.moneymate.domain.User
import isel.pt.moneymate.services.UsersService
import isel.pt.moneymate.utils.Uris
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class UsersController(
    private val usersService: UsersService
) {

    @PostMapping(Uris.Authentication.REGISTER)
    fun register(@Valid @RequestBody userData : RegisterInputModel): ResponseEntity<*> {
        val registerData = usersService.register(userData)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(registerData)
    }

    @PostMapping(Uris.Authentication.LOGIN)
    fun login(@Valid @RequestBody userData : LoginInputModel): ResponseEntity<*> {
        val loginData = usersService.login(userData)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loginData)
    }

    @PostMapping(Uris.Authentication.REFRESH_TOKEN)
    fun refreshToken(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ) {
        usersService.refreshToken(request, response)
    }

    @GetMapping(Uris.Users.GET_BY_ID)
    fun getUserById(@PathVariable id : Int): ResponseEntity<*> {
        val user = usersService.getUser(id)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(user)
    }

    @GetMapping(Uris.Users.GET_ALL_USERS)
    fun getUsers(): ResponseEntity<*> {
        val users = usersService.getUsers()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(users)
    }

    @PatchMapping(Uris.Users.UPDATE)
    fun updateUserName(@RequestBody userEditInput: UserEditInputModel, @PathVariable userId: Int): ResponseEntity<*> {
        val editedUser = usersService.updateUser(userId, userEditInput.username)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(editedUser)
    }
}