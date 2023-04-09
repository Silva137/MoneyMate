package isel.pt.moneymate.controller

import isel.pt.moneymate.controller.models.RegisterInputModel
import isel.pt.moneymate.services.UsersService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UsersController(private val usersService: UsersService) {

    @PostMapping("/users/register")
    fun register(@Valid @RequestBody userData : RegisterInputModel): ResponseEntity<*> {
        val user = usersService.register(userData.toRegisterInputDTO())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(user)
    }

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id : Int): ResponseEntity<*> {
        val user = usersService.getUser(id)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(user)
    }
}