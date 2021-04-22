package it.polito.wa2group8.wallet.domain


import java.sql.Timestamp
import javax.persistence.*

@Entity
class EmailVerificationToken(
    @Id
    @GeneratedValue
    var tokenId: Long?,
    var expiryDate: Timestamp,
    var token: String,
    @OneToOne(cascade = [(CascadeType.ALL)], fetch= FetchType.LAZY)
    @JoinColumn(name = "username")
    var user: User
)