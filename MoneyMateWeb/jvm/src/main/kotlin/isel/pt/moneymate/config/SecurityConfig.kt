package isel.pt.moneymate.config

import isel.pt.moneymate.utils.Uris
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutHandler

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtAuthFilter: JwtAuthenticationFilter,
    private val authenticationProvider: AuthenticationProvider,
    private val logoutHandler: LogoutHandler,
    private val exceptionHandler: ExceptionHandlerFilter
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(Uris.Authentication.REGISTER, Uris.Authentication.LOGIN, Uris.Authentication.REFRESH_TOKEN, Uris.Authentication.LOGOUT).permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(exceptionHandler, JwtAuthenticationFilter::class.java)
            .logout()
            .logoutUrl(Uris.Authentication.LOGOUT)
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler { request, response, authentication -> SecurityContextHolder.clearContext() }

        return http.build()
    }
}
