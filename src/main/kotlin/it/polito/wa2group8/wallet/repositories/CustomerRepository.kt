package it.polito.wa2group8.wallet.repositories

import it.polito.wa2group8.wallet.domain.Customer
import it.polito.wa2group8.wallet.domain.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: CrudRepository<Customer, Long>
{
    fun deleteCustomerByUser(user: User)

    @Query("SELECT c FROM Customer c WHERE c.user.username = ?1")
    fun getCustomerByUsername(username: String) : Iterable<Customer>
}