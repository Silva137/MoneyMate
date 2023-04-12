package isel.pt.moneymate.controller

import isel.pt.moneymate.controller.models.LoginInputModel
import isel.pt.moneymate.controller.models.RegisterInputModel
import isel.pt.moneymate.services.UsersService
import isel.pt.moneymate.utils.Uris
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
        val registerData = usersService.register(userData.toRegisterInputDTO())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(registerData)
    }

    @GetMapping(Uris.Authentication.LOGIN)
    fun login(@Valid @RequestBody userData : LoginInputModel): ResponseEntity<*> {
        val loginData = usersService.login(userData)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loginData)
    }

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id : Int): ResponseEntity<*> {
        val user = usersService.getUser(id)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(user)
    }

    @GetMapping("/test")
    fun sayHello(): ResponseEntity<String?>? {
        return ResponseEntity.ok("Hello from secured endpoint")
    }
}