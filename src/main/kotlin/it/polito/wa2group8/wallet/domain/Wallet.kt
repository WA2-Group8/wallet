package it.polito.wa2group8.wallet.domain


import javax.persistence.*
import javax.validation.constraints.Min

class Wallet (
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    var walletId: Long? = null,

    @Column(name="customerId", nullable=false)
    var customerId: Long? = null,

    @Column(name="currentAmount", nullable=false)
    @Min(0,message="The value must be a positive or zero value")
    var currentAmount: Double,

    @OneToMany(mappedBy = "walletId",fetch = FetchType.LAZY)
    var pList: List<Transaction>, //Purchases list

    @OneToMany(mappedBy = "walletId",fetch = FetchType.LAZY)
    var rList: List<Transaction>  // Recharges list
){}