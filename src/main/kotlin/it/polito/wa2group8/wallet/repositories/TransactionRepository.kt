package it.polito.wa2group8.wallet.repositories

import it.polito.wa2group8.wallet.domain.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

//This interface persists and retrieves "Transaction" entity into/from the DBMS offering CRUD methods
@Repository
interface TransactionRepository : CrudRepository<Transaction, Long>
{
    fun findByTimeInstantBetween(before: LocalDateTime, after: LocalDateTime)
}
