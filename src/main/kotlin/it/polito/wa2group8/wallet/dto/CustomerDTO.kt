package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.Customer

data class CustomerDTO(val name: String,
                       val email: String,
                       val deliveryAddress: String)

fun Customer.toCustomerDTO() = CustomerDTO("$name $surname", email, deliveryAddress)