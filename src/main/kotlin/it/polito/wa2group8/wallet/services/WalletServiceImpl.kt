package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.Transaction
import it.polito.wa2group8.wallet.domain.Wallet
import it.polito.wa2group8.wallet.dto.TransactionDTO
import it.polito.wa2group8.wallet.dto.WalletDTO
import it.polito.wa2group8.wallet.dto.toTransactionDTO
import it.polito.wa2group8.wallet.dto.toWalletDTO
import it.polito.wa2group8.wallet.repositories.CustomerRepository
import it.polito.wa2group8.wallet.repositories.TransactionRepository
import it.polito.wa2group8.wallet.repositories.WalletRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Service
@Transactional
class WalletServiceImpl(
    val transactionRepo: TransactionRepository,
    val walletRepo: WalletRepository,
    val customerRepo: CustomerRepository
) : WalletService {

    override fun createWallet (customerId: Long) : WalletDTO? {
        // Get Customer
        val customer = customerRepo.findByIdOrNull(customerId) ?: throw RuntimeException("Customer not found")

        // Create Wallet entity
        val wallet = Wallet(null, customer, BigDecimal(0))

        // Save wallet
        return walletRepo.save(wallet).toWalletDTO()
    }

    override fun getWalletById(id: Long) : WalletDTO? {
        return walletRepo.findByIdOrNull(id)?.toWalletDTO()
    }

    override fun createTransaction(payerWalletID: Long, transactionDTO: TransactionDTO) : TransactionDTO? {
        // Check if payer and beneficiary are different
        if(payerWalletID == transactionDTO.beneficiaryWalletID) throw RuntimeException("Beneficiary and Payer wallet must be different")

        // Get wallets
        val payerWallet = walletRepo.findByIdOrNull(payerWalletID) ?: throw RuntimeException("Payer wallet not found")
        val beneficiaryWallet = walletRepo.findByIdOrNull(transactionDTO.beneficiaryWalletID) ?: throw RuntimeException("Beneficiary wallet not found")

        // Create Transaction entity
        val transaction = Transaction(null, transactionDTO.amount.toBigDecimal(), LocalDateTime.now(), payerWallet, beneficiaryWallet)

        // Check if currentAmount is sufficient
        if(payerWallet.currentAmount < transaction.amount)
            throw RuntimeException("Payer money not sufficient")

        // Update payer wallet
        payerWallet.currentAmount -= transaction.amount
        walletRepo.save(payerWallet)

        // Update beneficiary wallet
        beneficiaryWallet.currentAmount += transaction.amount
        walletRepo.save(beneficiaryWallet)

        // Save transaction
        return transactionRepo.save(transaction).toTransactionDTO()
    }

    override fun getTransactionsByWalletId(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime) : List<TransactionDTO> {
        return transactionRepo.findByWalletIdAndTimeInstantBetween(walletId, endDate, startDate).map { t -> t.toTransactionDTO() }
    }

    override fun getTransactionById(walletId: Long, transactionId: Long) : TransactionDTO? {
        //TODO(Check Wallet)
        return transactionRepo.findByIdOrNull(transactionId)?.toTransactionDTO()
    }
}