package isel.pt.moneymate.exceptions

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionController : ResponseEntityExceptionHandler() {

    private fun exceptionResponse(
        name: String,
        message: String,
        status: HttpStatus,
        headers: HttpHeaders = HttpHeaders(),
    ): ResponseEntity<Any> {
        return ResponseEntity
            .status(status)
            .contentType(ProblemJson.MEDIA_TYPE)
            .headers(headers)
            .body(ProblemJson(name, message, status.value()))
    }

    @ExceptionHandler(ApiExceptions::class)
    fun handleApiException(
        exception: ApiExceptions,
        req: HttpServletRequest
    ): ResponseEntity<Any> = exceptionResponse(
        exception.name,
        exception.message,
        exception.status
    )

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = ex.allErrors.map { error -> error.defaultMessage ?: "Validation error" }
        val errorMessage = "Validation failed: ${errors.joinToString()}"

        return exceptionResponse(
            "Validation Failed",
            errorMessage,
            HttpStatus.BAD_REQUEST,
            headers
        )
    }
}