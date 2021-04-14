package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.Wallet
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin

data class WalletDTO(val customerId : Long?,
                     @get:DecimalMin(value="0.0", inclusive=true) val currentAmount : BigDecimal?
)


fun Wallet.toWalletDTO() = WalletDTO(customer.customerID, currentAmount)