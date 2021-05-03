package it.polito.wa2group8.wallet.domain

import javax.persistence.*

@Entity
class Customer(
    @Column(nullable = true)
    var name: String?,
    @Column(nullable = true)
    var surname: String?,
    @Column(nullable = true)
    var deliveryAddress: String?,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name="user_id",referencedColumnName = "id")
    var user: User
): EntityBase<Long>()
{
    @OneToMany(mappedBy = "id", targetEntity = Wallet::class, fetch = FetchType.LAZY)
    var walletList: MutableSet<Wallet> = mutableSetOf()
}
