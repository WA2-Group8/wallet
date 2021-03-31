package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.dto.TransactionDTO
import java.time.LocalDateTime

interface TransactionService
{
    //TODO("Handle exceptions and check return values accordingly")
    /**
     * Create a new transaction.
     * It returns the created transaction if it didn't already exist
     */
    fun createTransaction(payerWalletID: Long, beneficiaryWallet: Long, amount: Double, transactionType: String) : TransactionDTO

    /**
     * Get the details of a transaction given its ID.
     * A nullable TransactionDTO objects is returned since a transaction ID might not even exist.
     */
    fun getTransactionById(transactionID: Long): TransactionDTO?

    /**
     * Get the list of transactions given a walletID.
     * An empty list will be returned if no transaction meets the requirements.
     */

    fun getTransactionsByWalletId(walletId: Long) : List<TransactionDTO>

    /**
     * Get the list of transactions given a walletID between two dates.
     * An empty list will be returned if no transaction meets the requirements.
     */
    fun getTransactionsByWalletIdAndTimeFrame(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime) : List<TransactionDTO>
}
