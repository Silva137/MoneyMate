package isel.pt.moneymate.http.pipeline

import jakarta.servlet.FilterChain
import jakarta.servlet.http.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RequestFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("Received ${request.method} request to ${request.requestURI}")

        filterChain.doFilter(request, response)
    }

    companion object {
        private val log = LoggerFactory.getLogger(RequestFilter::class.java)
    }
}

