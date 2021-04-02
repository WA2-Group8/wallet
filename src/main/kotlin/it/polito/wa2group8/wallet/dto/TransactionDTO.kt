package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.Transaction
import java.time.LocalDateTime

/**
 * This data class models an information offered/consumed by the service
 */
data class TransactionDTO(val type: Transaction.TransactionTypes, val amount: Double, val date: LocalDateTime)

/**
 * An extension function to translate incoming data from the Controller layer to a DTO to be provided to the Service layer
 */
fun Transaction.toTransactionDTO() : TransactionDTO
{
    return TransactionDTO(this.transactionType, this.amount.toDouble(), this.timeInstant)
}

fun TransactionDTO.toEntity() : Transaction {
    TODO("To be implemented")
}