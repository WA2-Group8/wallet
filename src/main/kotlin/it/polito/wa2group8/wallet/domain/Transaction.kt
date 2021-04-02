package it.polito.wa2group8.wallet.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Pattern

//This class maps a transaction to a DBMS
@Entity
//@Table(name = "transaction")
class Transaction(
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    var transactionID: Long? = null,

    //The amount of money transacted
    @DecimalMin(value="0.1", message="The transaction amount must be greater than 0")
    @Column(name = "amount", nullable = false)
    var amount: BigDecimal,

    //When the transaction was performed
    @Column(name = "time_instant", nullable = false)
    var timeInstant: LocalDateTime,

    //The wallet which the money was taken from
    //TODO("Uncomment when real class "Wallet" will be present")
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "wallet_id")
    //payerWallet: Wallet,

    //The wallet which the money was given to
    //TODO("Uncomment when real class "Wallet" will be present")
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "wallet_id")
    //beneficiaryWallet: Wallet,

    //Transaction type
    @Column(name = "transaction_type", nullable = false)
    @Pattern(regexp = "purchase|recharge", message="Invalid type of transaction")
    var transactionType: TransactionTypes
    )
{
    enum class TransactionTypes(val type: String)
    {
        Purchase("purchase"), //A transaction that reduces the amount of money in the wallet
        Recharge("recharge"), //A transaction that contributes to increase the money in the wallet
    }
}