package it.polito.wa2group8.wallet.domain

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
//@Table(name="Wallet")
class Wallet (
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    var walletId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId",referencedColumnName = "customerId")
    var customer: Customer,

    @Column(name="currentAmount", nullable=false)
    @get:Min(value= 0,message="The value must be a positive or zero value")
    var currentAmount: BigDecimal

){
    @OneToMany(mappedBy = "payerWallet",targetEntity=Transaction::class,fetch = FetchType.LAZY)
    var pList: MutableList<Transaction> = mutableListOf() //Purchases list

    @OneToMany(mappedBy = "beneficiaryWallet",targetEntity=Transaction::class,fetch = FetchType.LAZY)
    var rList: MutableList<Transaction> = mutableListOf() // Recharges list
}