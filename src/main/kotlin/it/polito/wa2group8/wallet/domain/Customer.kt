package it.polito.wa2group8.wallet.domain;


import javax.persistence.*;

@Entity
//@Table(name = customers)
class Customer(
    @Id
    @GeneratedValue
    var customerID: Long?,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var surname: String,
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false)
    var deliveryAddress: String
)
