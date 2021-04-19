package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.User
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UserDetailsDTO(
    val userId: Long?,
    @get:NotEmpty val username: String?,
    val password: String?,
    val email: String,
    val roles: String
)

fun User.toUserDetailsDTO() = UserDetailsDTO(userId,username,null,email,roles)