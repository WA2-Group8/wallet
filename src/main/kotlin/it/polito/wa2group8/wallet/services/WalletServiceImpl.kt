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

    override fun createWallet (walletDTO: WalletDTO) : WalletDTO? {
        // Get Customer
        val customer = customerRepo.findByIdOrNull(walletDTO.customerId ?: -1) ?: return null

        // Create Wallet entity
        val wallet = Wallet(null, customer,walletDTO.currentAmount)

        // Save wallet
        return walletRepo.save(wallet).toWalletDTO()
    }

    override fun getWalletById(id: Long) : WalletDTO? {
        return walletRepo.findByIdOrNull(id)?.toWalletDTO()
    }

    override fun createTransaction(transactionDTO: TransactionDTO) : TransactionDTO? {
        // Get wallets
        val payerWallet = walletRepo.findByIdOrNull(transactionDTO.payerWalletID) ?: return null
        val beneficiaryWallet = walletRepo.findByIdOrNull(transactionDTO.beneficiaryWalletID) ?: return null

        // Convert date
        val instant = Instant.ofEpochMilli(transactionDTO.dateInMillis)
        val date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()

        // Create Transaction entity
        val transaction = Transaction(null, transactionDTO.amount.toBigDecimal(), date, payerWallet, beneficiaryWallet, transactionDTO.type)

        // Save transaction
        return transactionRepo.save(transaction).toTransactionDTO()
    }

    override fun getTransactionsByWalletId(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime) : List<TransactionDTO> {
        return transactionRepo.findByWalletIdAndTimeInstantBetween(walletId, endDate, startDate).map { t -> t.toTransactionDTO() }
    }

    override fun getTransactionById(transactionId: Long) : TransactionDTO? {
        return transactionRepo.findByIdOrNull(transactionId)?.toTransactionDTO()
    }
}