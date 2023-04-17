package isel.pt.moneymate.http.controller


import isel.pt.moneymate.http.models.users.LoginUserDTO
import isel.pt.moneymate.http.models.users.CreateUserDTO
import isel.pt.moneymate.http.models.users.UpdateUserDTO
import isel.pt.moneymate.services.UsersService
import isel.pt.moneymate.http.utils.Uris
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
    fun register(@Valid @RequestBody userData : CreateUserDTO): ResponseEntity<*> {
        val registerData = usersService.register(userData)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(registerData)
    }

    @PostMapping(Uris.Authentication.LOGIN)
    fun login(@Valid @RequestBody userData : LoginUserDTO): ResponseEntity<*> {
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
    fun updateUserName(@RequestBody userEditInput: UpdateUserDTO, @PathVariable userId: Int): ResponseEntity<*> {
        val editedUser = usersService.updateUser(userId, userEditInput.username)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(editedUser)
    }
}