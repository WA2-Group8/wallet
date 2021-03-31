package it.polito.wa2group8.wallet.repositories

import it.polito.wa2group8.wallet.domain.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.transaction.Transactional

//This interface persists and retrieves "Transaction" entity into/from the DBMS offering CRUD methods
@Repository
@Transactional()
interface TransactionRepository : CrudRepository<Transaction, Long>
{
    //Get wallet's list of transaction by walletID.
    //TODO("Uncomment when real class "Wallet" will be present")
    //fun findByWallet(wallet: Wallet): List<Transaction>

    //Get all wallet's transactions in a given data range by walletID.
    //TODO("Uncomment when real class "Wallet" will be present")
    //fun findByWalletAndTimeInstantBetween(wallet: Wallet, before: LocalDateTime, after: LocalDateTime) : List<Transaction>
}
