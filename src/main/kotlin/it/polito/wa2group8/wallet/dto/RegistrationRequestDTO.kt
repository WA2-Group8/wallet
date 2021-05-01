package it.polito.wa2group8.wallet.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class RegistrationRequestDTO
(
    @get:NotEmpty val username: String,
    @get:NotEmpty val password: String,
    @get:NotEmpty val confirmPassword: String,
    @get:NotEmpty @get:Email val email: String,
)