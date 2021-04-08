package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.Wallet
import java.math.BigDecimal

data class WalletDTO(val customerId : Long?,
                     val currentAmount : Long)


fun Wallet.toWalletDTO() = WalletDTO(customerId, currentAmount)

fun WalletDTO.toEntity() = Wallet(null,customerId,currentAmount)