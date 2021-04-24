package it.polito.wa2group8.wallet.security

import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication

class JwtUtils {

    @Value("\${application.jwt.jwtSecret}")
    private val jwtSecret : String = ""

    @Value("\${application.jwt.jwtExpirationMs}")
    private val jwtExpirationMs : Int = 0

    @Value("\${application.jwt.jwtHeader}")
    private val jwtHeader : String = ""

    @Value("\${application.jwt.jwtHeaderStart}")
    private val jwtHeaderStart : String = ""

    fun generateJwtToken (authentication: Authentication): String {

        return ""
    }

    fun validateJwtToken (authToken: String): Boolean {
        return false
    }

    fun getDetailsFromJwtToken (authToken: String): UserDetailsDTO {

    }
}