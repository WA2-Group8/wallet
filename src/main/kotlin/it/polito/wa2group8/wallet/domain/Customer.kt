package it.polito.wa2group8.wallet.domain


import javax.persistence.*

@Entity
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
    var deliveryAddress: String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name="userId",referencedColumnName = "userId")
    var user: User
){
    @OneToMany(mappedBy = "walletId", targetEntity = Wallet::class, fetch = FetchType.LAZY)
    var walletList: MutableSet<Wallet> = mutableSetOf()
}
