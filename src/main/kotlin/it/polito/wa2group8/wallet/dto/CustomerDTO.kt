package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.Customer
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CustomerDTO(@get:NotNull(message="Invalid customerId") @get:Positive(message="Invalid customerId") val customerId: Long?,
                       val name: String?,
                       val email: String?,
                       val deliveryAddress: String?)

fun Customer.toCustomerDTO() = CustomerDTO(this.getId(),"$name $surname", user.email, deliveryAddress)