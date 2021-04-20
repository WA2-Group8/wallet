package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.User
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class SignInBody(@get:NotEmpty val username: String, @get:Size(min = 8) val password: String)

data class UserDetailsDTO(
    val userId: Long?,
    @get:NotEmpty val username: String?,
    val password: String?,
    val email: String,
    val roles: String
)

fun User.toUserDetailsDTO() = UserDetailsDTO(userId,username,null,email,roles)