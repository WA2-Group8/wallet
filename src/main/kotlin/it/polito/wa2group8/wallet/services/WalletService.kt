package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.dto.TransactionDTO
import it.polito.wa2group8.wallet.dto.WalletDTO
import java.time.LocalDateTime

interface WalletService {
    /**
     * Create a new wallet for a certain customer
     */
    fun createWallet (walletDTO: WalletDTO) : WalletDTO?

    /**
     * Get the details of a wallet given its ID.
     * A nullable WalletDTO objects is returned since a wallet ID might not even exist.
     */
    fun getWalletById(id: Long) : WalletDTO?

    /**
     * Create a new transaction if it didn't already exist
     * Return the DTO of the created transaction or null in case of error.
     */
    fun createTransaction(transactionDTO: TransactionDTO) : TransactionDTO?

    /**
     * Get the list of transactions given a walletID between two dates.
     * An empty list will be returned if no transaction meets the requirements.
     */
    fun getTransactionsByWalletId(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime) : List<TransactionDTO>

    /**
     * Get the details of a transaction given its ID.
     * A nullable TransactionDTO objects is returned since a transaction ID might not even exist.
     */
    fun getTransactionById(transactionId: Long) : TransactionDTO?
}