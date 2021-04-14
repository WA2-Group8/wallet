package it.polito.wa2group8.wallet.repositories

import it.polito.wa2group8.wallet.domain.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: CrudRepository<Customer, Long>