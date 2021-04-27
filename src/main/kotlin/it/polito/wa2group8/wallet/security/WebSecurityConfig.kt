package it.polito.wa2group8.wallet.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class WebSecurityConfig: WebSecurityConfigurerAdapter() {

    @Autowired
    val authEntryPoint = CustomAuthenticationEntryPoint()

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder())

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

}