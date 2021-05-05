package it.polito.wa2group8.wallet.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.DecimalMin

//This class maps a transaction to a DBMS
@Entity
class Transaction(
    //The amount of money transacted
    @get:DecimalMin(value="0.0", message="The transaction amount must be greater than 0", inclusive=false)
    @Column(name = "amount", nullable = false)
    var amount: BigDecimal,

    //When the transaction was performed
    @Column(name = "time_instant", nullable = false)
    var timeInstant: LocalDateTime,

    //The wallet which the money was taken from
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payerWallet",referencedColumnName = "id")
    var payerWallet: Wallet,

    //The wallet which the money was given to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiaryWallet",referencedColumnName = "id")
    var beneficiaryWallet: Wallet
): EntityBase<Long>()
