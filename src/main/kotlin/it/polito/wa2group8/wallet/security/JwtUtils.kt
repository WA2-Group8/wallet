package it.polito.wa2group8.wallet.security

import io.jsonwebtoken.Jwts
import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import it.polito.wa2group8.wallet.services.UserDetailsService
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class JwtUtils (val userDetailsService: UserDetailsService){

    @Value("\${application.jwt.jwtSecret}")
    private val jwtSecret : String = ""

    @Value("\${application.jwt.jwtExpirationMs}")
    private val jwtExpirationMs : Int = 0

    @Value("\${application.jwt.jwtHeader}")
    private val jwtHeader : String = ""

    @Value("\${application.jwt.jwtHeaderStart}")
    private val jwtHeaderStart : String = ""

    fun generateJwtToken (authentication: Authentication): String {
        val decodedKey: ByteArray = Base64.getDecoder().decode(jwtSecret)
        val originalKey: SecretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "ES256")

        return Jwts.builder()
            .setIssuer(authentication.principal.toString())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            //.signWith(SignatureAlgorithm.ES256, jwtSecret)
            .signWith(originalKey)
            .compact()
    }

    fun validateJwtToken (authToken: String): Boolean {
        try {
            val decodedKey: ByteArray = Base64.getDecoder().decode(jwtSecret)
            val originalKey: SecretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "ES256")
            val body = Jwts.parserBuilder().setSigningKey(originalKey).build().parseClaimsJws(authToken).body
            if (body.expiration.time < System.currentTimeMillis()) return false
            return true
        } catch (ex: Exception) {
            return false
        }
    }

    fun getDetailsFromJwtToken (authToken: String): UserDetailsDTO {
        val decodedKey: ByteArray = Base64.getDecoder().decode(jwtSecret)
        val originalKey: SecretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "ES256")
        // Get information from token
        val body = Jwts.parserBuilder().setSigningKey(originalKey).build().parseClaimsJws(authToken).body
        // Get UserDetails from DB
        return userDetailsService.loadUserByUsername(body.issuer)
    }
}