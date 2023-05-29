package isel.pt.moneymate.config

import isel.pt.moneymate.http.utils.Uris
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
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtAuthFilter: JwtAuthenticationFilter,
    private val authenticationProvider: AuthenticationProvider,
    private val customLogoutHandler: LogoutSuccessHandler,
    private val exceptionHandler: ExceptionHandlerFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .cors().and()
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
            .logoutSuccessHandler(customLogoutHandler)

        return http.build()
    }



    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*") // allow requests from any origin
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // allow HTTP methods
        configuration.allowedHeaders = listOf("*") // allow all headers

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}
