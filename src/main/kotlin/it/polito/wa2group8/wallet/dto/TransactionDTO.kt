package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.Transaction
import java.time.ZoneOffset

/**
 * This data class models an information offered/consumed by the service
 */
data class TransactionDTO(val amount: Double,
                          val dateInMillis: Long,
                          val payerWalletID: Long,
                          val beneficiaryWalletID: Long)

/**
 * An extension function to translate incoming data from the Controller layer to a DTO to be provided to the Service layer
 */
fun Transaction.toTransactionDTO() = TransactionDTO(this.amount.toDouble(),
                                                    this.timeInstant.toInstant(ZoneOffset.UTC).toEpochMilli(),
                                                    this.payerWallet.walletId ?: -1,
                                                    this.beneficiaryWallet.walletId ?: -1)


