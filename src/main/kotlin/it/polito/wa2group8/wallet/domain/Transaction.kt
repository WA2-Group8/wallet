package it.polito.wa2group8.wallet.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Pattern

//This class maps a transaction to a DBMS
@Entity
class Transaction(
    @Id
    @Column(name = "transaction_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    var transactionID: Long? = null,

    //The amount of money transacted
    @get:DecimalMin(value="0.0", message="The transaction amount must be greater than 0", inclusive=false)
    @Column(name = "amount", nullable = false)
    var amount: BigDecimal,

    //When the transaction was performed
    @Column(name = "time_instant", nullable = false)
    var timeInstant: LocalDateTime,

    //The wallet which the money was taken from
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payerWallet",referencedColumnName = "walletId")
    var payerWallet: Wallet,

    //The wallet which the money was given to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiaryWallet",referencedColumnName = "walletId")
    var beneficiaryWallet: Wallet,

    //Transaction type
    @Column(name = "transaction_type", nullable = false)
    @get:Pattern(regexp = "purchase|recharge", message="Invalid type of transaction")
    var transactionType: TransactionTypes
    )
{
    enum class TransactionTypes(val type: String)
    {
        Purchase("purchase"), //A transaction that reduces the amount of money in the wallet
        Recharge("recharge"), //A transaction that contributes to increase the money in the wallet
    }
}
