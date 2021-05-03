package it.polito.wa2group8.wallet.security

import io.jsonwebtoken.*
import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import it.polito.wa2group8.wallet.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import it.polito.wa2group8.wallet.services.UserDetailsService
import javassist.NotFoundException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
class JwtUtils (val userDetailsService: UserDetailsService, val userRepository: UserRepository){

    @Value("\${application.jwt.jwtSecret}")
    private val jwtSecret : String = ""

    @Value("\${application.jwt.jwtExpirationMs}")
    private val jwtExpirationMs : Int = 0


    fun generateJwtToken (authentication: Authentication): String {
        //val decodedKey: ByteArray = Base64.getDecoder().decode(jwtSecret)

        //val originalKey: SecretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, HS256)
        println(authentication.principal.toString())
        val issuer = authentication.principal as UserDetailsDTO
        return Jwts.builder()
            .setIssuer(issuer.username)
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            //.signWith(SignatureAlgorithm.ES256, jwtSecret)
            .signWith(SignatureAlgorithm.HS256,jwtSecret)
            .compact()
    }

    fun validateJwtToken (authToken: String): Boolean {
        try {
            //val decodedKey: ByteArray = Base64.getDecoder().decode(jwtSecret)
            //val originalKey: SecretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "ES256")
            /*val body =*/ Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            //if (body.expiration.time < System.currentTimeMillis()) return false
            return true
        } /*catch (ex: Exception) {
            return false
        }*/
        catch (ex: SignatureException) {
            println(ex.message)
            return false
        } catch (ex: MalformedJwtException ) {
            println(ex.message)
            return false
        } catch (ex: ExpiredJwtException) {
            println(ex.message)
            return false
        } catch (ex: UnsupportedJwtException) {
            println(ex.message)
            return false
        } catch (ex: IllegalArgumentException) {
            println(ex.message)
            return false
        }


    }

    fun getDetailsFromJwtToken (authToken: String): UserDetailsDTO {
        //val decodedKey: ByteArray = Base64.getDecoder().decode(jwtSecret)
        //val originalKey: SecretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "ES256")
        // Get information from token
        val body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).body
        // Get UserDetails from DB
        println(body.toString())
        val user = userRepository.findByUsername(body.issuer) ?: throw NotFoundException("User not found")
        println(user.getRolenames())
        return UserDetailsDTO(user.username, null, null, null, user.getRolenames())
    }
}