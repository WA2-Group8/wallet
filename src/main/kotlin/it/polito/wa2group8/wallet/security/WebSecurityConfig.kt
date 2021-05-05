package it.polito.wa2group8.wallet.security

import it.polito.wa2group8.wallet.services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
class WebSecurityConfig(val jwtUtils: JwtUtils, val userDetailsService: UserDetailsService): WebSecurityConfigurerAdapter() {

    @Autowired
    val authEntryPoint = CustomAuthenticationEntryPoint()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())

    }
    override fun configure(http: HttpSecurity) {
        //csrf is enable by default
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authEntryPoint)
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/auth/**")
            .permitAll()
        .and()
            .authorizeRequests()
            .antMatchers("/**")
            .hasAuthority("CUSTOMER")
        .and()
            .logout()
            .permitAll()


        http.addFilterBefore(JwtAuthenticationTokenFilter(jwtUtils),
            UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Component
    class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
        override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized")
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
            //Get "Authorization" header
            val header = request.getHeader("Authorization")

            //Verify that "Authorization" header is present
            //This check is very important to allow access to entry points marked as "permitAll"
            if (header == null || !header.startsWith("Bearer"))
            {
                filterChain.doFilter(request, response)
                return
            }


            //Retrieve the JWT from the "Authorization" header
            val token = header.replace("Bearer ", "")

            val userDetails = if(jwtUtils.validateJwtToken(token))
                jwtUtils.getDetailsFromJwtToken(token) else return

            //Create an UsernamePasswordAuthenticationToken
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails,
                null, //Password must be always null here
                userDetails.authorities //Granted authorities (i.e. the description of the details of the authentication of a Principal)
            )
            //Add extra details coming from the request
            authentication.details = WebAuthenticationDetailsSource()
                                        .buildDetails(request)

            //Set the authentication Object in the security context
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)

        }
    }
}

