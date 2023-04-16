package isel.pt.moneymate.exceptions

import com.google.gson.Gson
import org.springframework.http.HttpStatus


open class ApiExceptions(val name: String, override val message: String, val status: HttpStatus): Exception(message){
    fun toJson(): String {
        return Gson().toJson(mapOf(
            "name" to this.javaClass.simpleName,
            "message" to this.message,
            "status" to this.status.value()
        ))
    }
}

class InvalidLoginException (message: String): ApiExceptions("Invalid Username or Password", message, HttpStatus.UNAUTHORIZED)
class InvalidParameterException (message: String): ApiExceptions("Invalid Parameter", message, HttpStatus.BAD_REQUEST)
class NotFoundException (message: String): ApiExceptions("Not Found", message, HttpStatus.NOT_FOUND)
class AlreadyExistsException (message: String): ApiExceptions("Resource Already Exists", message, HttpStatus.CONFLICT)
class TokenExpiredException (message: String): ApiExceptions("Token Expired", message, HttpStatus.UNAUTHORIZED)
class AuthenticationException (message: String): ApiExceptions("Authentication Error", message, HttpStatus.UNAUTHORIZED)

class UnauthorizedException (message: String): ApiExceptions("Unauthorized", message, HttpStatus.UNAUTHORIZED)
class ForbiddenException (message: String): ApiExceptions("Forbidden", message, HttpStatus.FORBIDDEN)
class InternalServerErrorException (message: String): ApiExceptions("Internal Server Error", message, HttpStatus.INTERNAL_SERVER_ERROR)
class ServiceUnavailableException (message: String): ApiExceptions("Service Unavailable", message, HttpStatus.SERVICE_UNAVAILABLE)
class BadRequestException (message: String): ApiExceptions("Bad Request", message, HttpStatus.BAD_REQUEST)
class ConflictException (message: String): ApiExceptions("Conflict", message, HttpStatus.CONFLICT)
class UnprocessableEntityException (message: String): ApiExceptions("Unprocessable Entity", message, HttpStatus.UNPROCESSABLE_ENTITY)
class GatewayTimeoutException (message: String): ApiExceptions("Gateway Timeout", message, HttpStatus.GATEWAY_TIMEOUT)

