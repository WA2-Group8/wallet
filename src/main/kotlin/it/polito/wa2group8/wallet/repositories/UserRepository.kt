package it.polito.wa2group8.wallet.repositories

import it.polito.wa2group8.wallet.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {

    fun findByUsername(username: String): User?
}