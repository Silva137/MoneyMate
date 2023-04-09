package isel.pt.moneymate.controller.pipeline

import jakarta.servlet.FilterChain
import jakarta.servlet.http.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ResponseFilter : HttpFilter() {

    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val wrappedRequest = HttpServletRequestWrapper(request)
        val wrappedResponse = HttpServletResponseWrapper(response)

        log.info("doFilter: method='{}', uri='{}'", request.method, request.requestURI)
        chain.doFilter(wrappedRequest, wrappedResponse)
        log.info( "doFilter: content-type='{}'", response.contentType)
        log.info("doFilter: ending")
    }

    companion object {
        private val log = LoggerFactory.getLogger(ResponseFilter::class.java)
    }
}