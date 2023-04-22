package isel.pt.moneymate.config


import isel.pt.moneymate.exceptions.AuthenticationException
import isel.pt.moneymate.repository.TokensRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter (
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val tokensRepository: TokensRepository,
)  : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath.contains("/auth")) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader("Authorization") ?: throw AuthenticationException("Missing Authorization header")
        if (!authHeader.startsWith("Bearer ")) {
            throw AuthenticationException("Authorization header must be a Bearer token")
        }

        val jwt = authHeader.substring(7)
        val userEmail = jwtService.extractUsername(jwt)
        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(userEmail)
            val token = tokensRepository.findByToken(jwt)
            val isTokenValid = token?.run { !expired && !revoked } ?: false

            if (!jwtService.isTokenExpired(jwt) && isTokenValid) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }
}

