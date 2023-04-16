package isel.pt.moneymate.config

import io.jsonwebtoken.ExpiredJwtException
import isel.pt.moneymate.exceptions.AuthenticationException
import isel.pt.moneymate.exceptions.TokenExpiredException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class ExceptionHandlerFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            val jsonError = TokenExpiredException(e.message ?: "Token expired").toJson()
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, jsonError)

        } catch (e: AuthenticationException) {
            val jsonError = AuthenticationException(e.message).toJson()
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, jsonError)
        }
    }

    fun setErrorResponse(status: HttpStatus, response: HttpServletResponse, jsonError: String) {
        response.status = status.value()
        response.contentType = "application/json"
        response.writer.write(jsonError)
    }
}