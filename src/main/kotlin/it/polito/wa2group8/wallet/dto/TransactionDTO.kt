package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.Transaction
import it.polito.wa2group8.wallet.domain.Wallet
import java.time.Instant
import java.time.ZoneOffset

/**
 * This data class models an information offered/consumed by the service
 */
data class TransactionDTO(val amount: Double,
                          val dateInMillis: Long,
                          val payerWallet: Wallet,
                          val beneficiaryWallet: Wallet,
                          val type: Transaction.TransactionTypes)

/**
 * An extension function to translate incoming data from the Controller layer to a DTO to be provided to the Service layer
 */
fun Transaction.toTransactionDTO() = TransactionDTO(this.amount.toDouble(),
                                                    this.timeInstant.toInstant(ZoneOffset.UTC).toEpochMilli(),
                                                    this.payerWallet,
                                                    this.beneficiaryWallet,
                                                    this.transactionType)


fun TransactionDTO.toTransactionEntity() = Transaction(null,
                                                        this.amount.toBigDecimal(),
                                                        Instant.ofEpochMilli(this.dateInMillis).atZone(ZoneOffset.UTC).toLocalDateTime(),
                                                        this.payerWallet,
                                                        this.beneficiaryWallet,
                                                        this.type)
