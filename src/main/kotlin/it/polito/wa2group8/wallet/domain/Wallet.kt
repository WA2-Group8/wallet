package it.polito.wa2group8.wallet.domain


import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Min

class Wallet (
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    var walletId: Long? = null,

    @Column(name="customerId", nullable=false)
    var customerId: Long? = null,

    @Column(name="currentAmount", nullable=false)
    @Min(0,message="The value must be a positive or zero value")
    var currentAmount: Int,

    @Column(name="idTP", nullable=false)
    var idTP: Long, //Purchases list Id

    @Column(name="idTR",nullable=false)
    var idTR: Long  // Recharges list Id
){}