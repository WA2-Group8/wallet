package it.polito.wa2group8.wallet.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class RegistrationRequestDTO
(
    @get:NotEmpty val username: String,
    @get:Size(min = 8) val password: String,
    @get:Size(min = 8) val confirmPassword: String,
    @get:NotEmpty @get:Email val email: String,
)