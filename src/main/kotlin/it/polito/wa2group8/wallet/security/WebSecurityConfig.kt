package it.polito.wa2group8.wallet.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class WebSecurityConfig(val jwtUtils: JwtUtils): WebSecurityConfigurerAdapter() {

    @Autowired
    val authEntryPoint = CustomAuthenticationEntryPoint()

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailsService())
            .passwordEncoder(passwordEncoder())

    }
    override fun configure(http: HttpSecurity) {
        //csrf is enable by default
        http.cors()
            .and()
            .authorizeRequests()
            .antMatchers("/wallet/**")
            //.regexMatchers("?! /auth/.*")
            .hasAnyRole()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.exceptionHandling().authenticationEntryPoint(authEntryPoint)
        http.addFilterBefore(JwtAuthenticationTokenFilter(jwtUtils),
            UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Component
    class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
        override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
        }

    }
    @Component
    class JwtAuthenticationTokenFilter(val jwtUtils: JwtUtils): OncePerRequestFilter() {
        /**
         * Same contract as for `doFilter`, but guaranteed to be
         * just invoked once per request within a single request thread.
         * See [.shouldNotFilterAsyncDispatch] for details.
         *
         * Provides HttpServletRequest and HttpServletResponse arguments instead of the
         * default ServletRequest and ServletResponse ones.
         */
        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            val header = request.getHeader("Authorization")
            val token = header.replace("Bearer ", "")

            val userDetails = if(jwtUtils.validateJwtToken(token))
                jwtUtils.getDetailsFromJwtToken(token) else throw RuntimeException()

            val authentication = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                //userDetails.authorities
            )
            authentication.details = WebAuthenticationDetailsSource()
                                        .buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }
    }
}

