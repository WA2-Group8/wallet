package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.Wallet

data class WalletDTO(val customerId : Long?,
                     val currentAmount : Double)


fun Wallet.toWalletDTO() = WalletDTO(customerId, currentAmount)