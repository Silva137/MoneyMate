package isel.pt.moneymate.controller.models

import isel.pt.moneymate.services.dtos.RegisterInputDTO
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterInputModel(
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    val password: String
){
    fun toRegisterInputDTO() = RegisterInputDTO(username, email, password)
}
