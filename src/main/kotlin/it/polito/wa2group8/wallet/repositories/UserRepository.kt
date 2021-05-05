package it.polito.wa2group8.wallet.repositories

import it.polito.wa2group8.wallet.domain.User
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {

    fun findByUsername(username: String): User?

    @Modifying
    @Query("UPDATE User u SET u.isEnabled=true WHERE u.username = ?1")
    fun enableUser(username: String)

    @Modifying
    @Query("UPDATE User u SET u.isEnabled=false WHERE u.username = ?1")
    fun disableUser(username: String)
}