package isel.pt.moneymate.controller

import isel.pt.moneymate.services.UsersService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController(private val usersService: UsersService) {

    @GetMapping("TODO")
    fun getUser(@PathVariable id : Int): ResponseEntity<*> {
        val user = usersService.getUser(id)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(user)
    }
}