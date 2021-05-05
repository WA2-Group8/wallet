package it.polito.wa2group8.wallet.repositories

import it.polito.wa2group8.wallet.domain.Transaction
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

//This interface persists and retrieves "Transaction" entity into/from the DBMS offering CRUD methods
@Repository
interface TransactionRepository : CrudRepository<Transaction, Long>
{
    @Query(
        "SELECT t FROM Transaction t WHERE (t.beneficiaryWallet.id = ?1 OR t.payerWallet.id = ?1) AND t.timeInstant >= ?2 AND t.timeInstant <= ?3"
    )
    fun findByWalletIdAndTimeInstantBetween(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime) : Iterable<Transaction>

}
