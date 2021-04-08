package it.polito.wa2group8.wallet.domain


import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
@Table(name="Wallet")
class Wallet (
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    var walletId: Long? = null,

    @Column(name="customerId", nullable=false)
    var customerId: Long? = null,

    @Column(name="currentAmount", nullable=false)
    @Min(0,message="The value must be a positive or zero value")
    var currentAmount: Long

){
    @OneToMany(mappedBy = "payerWallet",targetEntity=Transaction::class,fetch = FetchType.LAZY)
    var pList: MutableList<Transaction> = mutableListOf() //Purchases list

    @OneToMany(mappedBy = "beneficiaryWallet",targetEntity=Transaction::class,fetch = FetchType.LAZY)
    var rList: MutableList<Transaction> = mutableListOf() // Recharges list
}