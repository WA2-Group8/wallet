package it.polito.wa2group8.wallet.security

import io.jsonwebtoken.*
import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import it.polito.wa2group8.wallet.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import it.polito.wa2group8.wallet.services.UserDetailsService
import javassist.NotFoundException
import org.springframework.context.annotation.Configuration
import java.security.SignatureException
import java.util.*


@Configuration
class JwtUtils (val userDetailsService: UserDetailsService, val userRepository: UserRepository){

    @Value("\${application.jwt.jwtSecret}")
    private val jwtSecret : String = ""

    @Value("\${application.jwt.jwtExpirationMs}")
    private val jwtExpirationMs : Int = 0


    fun generateJwtToken (authentication: Authentication): String {
        val issuer = authentication.principal as UserDetailsDTO
        return Jwts.builder()
            .setIssuer(issuer.username)
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS256,jwtSecret)
            .compact()
    }

    fun validateJwtToken (authToken: String): Boolean {
        try {
            val body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).body
            if(!userRepository.findByUsername(body.issuer.toString())!!.isEnabled) return false
            return true
        }
        catch (ex: SignatureException) {
            return false
        } catch (ex: MalformedJwtException ) {
            return false
        } catch (ex: ExpiredJwtException) {
            return false
        } catch (ex: UnsupportedJwtException) {
            return false
        } catch (ex: IllegalArgumentException) {
            return false
        }


    }

    fun getDetailsFromJwtToken (authToken: String): UserDetailsDTO {
        // Get information from token
        val body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).body
        // Get UserDetails from DB
        val user = userRepository.findByUsername(body.issuer) ?: throw NotFoundException("User not found")

        return UserDetailsDTO(user.username, null, null, null, user.getRolenames())
    }
}