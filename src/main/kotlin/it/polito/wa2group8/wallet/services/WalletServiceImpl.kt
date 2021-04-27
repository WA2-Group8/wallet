package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.Transaction
import it.polito.wa2group8.wallet.domain.Wallet
import it.polito.wa2group8.wallet.dto.TransactionDTO
import it.polito.wa2group8.wallet.dto.WalletDTO
import it.polito.wa2group8.wallet.dto.toTransactionDTO
import it.polito.wa2group8.wallet.dto.toWalletDTO
import it.polito.wa2group8.wallet.exceptions.BadRequestException
import it.polito.wa2group8.wallet.exceptions.ForbiddenException
import it.polito.wa2group8.wallet.exceptions.NotFoundException
import it.polito.wa2group8.wallet.repositories.CustomerRepository
import it.polito.wa2group8.wallet.repositories.TransactionRepository
import it.polito.wa2group8.wallet.repositories.WalletRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
@Transactional
class WalletServiceImpl(
    val transactionRepo: TransactionRepository,
    val walletRepo: WalletRepository,
    val customerRepo: CustomerRepository
) : WalletService {

    override fun createWallet (customerId: Long) : WalletDTO? {
        // Get Customer
        val customer = customerRepo.findByIdOrNull(customerId) ?: throw NotFoundException("Customer not found")

        // Create Wallet entity
        val wallet = Wallet(customer, BigDecimal(0))

        // Save wallet
        return walletRepo.save(wallet).toWalletDTO()
    }

    override fun getWalletById(id: Long) : WalletDTO? {
        return walletRepo.findByIdOrNull(id)?.toWalletDTO() ?: throw NotFoundException("Wallet Not Found")
    }

    override fun createTransaction(payerWalletID: Long, transactionDTO: TransactionDTO) : TransactionDTO? {
        // Check if payer and beneficiary are different
        if(payerWalletID == transactionDTO.beneficiaryWalletID) throw BadRequestException("Beneficiary and Payer wallet must be different")

        // Get wallets
        val payerWallet = walletRepo.findByIdOrNull(payerWalletID) ?: throw NotFoundException("Payer wallet not found")
        val beneficiaryWallet = walletRepo.findByIdOrNull(transactionDTO.beneficiaryWalletID ?: -1) ?: throw NotFoundException("Beneficiary wallet not found")

        // Create Transaction entity
        val transaction = Transaction(transactionDTO.amount!!, LocalDateTime.now(), payerWallet, beneficiaryWallet)

        // Check if currentAmount is sufficient
        if(payerWallet.currentAmount < transaction.amount)
            throw BadRequestException("Payer money not sufficient")

        // Update payer wallet
        payerWallet.currentAmount -= transaction.amount
        walletRepo.save(payerWallet)

        // Update beneficiary wallet
        beneficiaryWallet.currentAmount += transaction.amount
        walletRepo.save(beneficiaryWallet)

        // Save transaction
        return transactionRepo.save(transaction).toTransactionDTO()
    }

    override fun getTransactionsByWalletId(walletId: Long, startDate: Long, endDate: Long) : List<TransactionDTO> {
        //Check if endDate > startDate
        if(endDate<startDate) throw  BadRequestException("End date must be after start date")

        // Check if wallet exist
        if(!walletRepo.existsById(walletId)) throw NotFoundException("Wallet not found")

        // Converting date
        val start = Instant.ofEpochMilli(startDate).atZone(ZoneOffset.UTC).toLocalDateTime()
        val end = Instant.ofEpochMilli(endDate).atZone(ZoneOffset.UTC).toLocalDateTime()

        // Get transactions of the specified wallet
        return transactionRepo.findByWalletIdAndTimeInstantBetween(walletId, start, end).map { t -> t.toTransactionDTO() }
    }

    override fun getTransactionById(walletId: Long, transactionId: Long) : TransactionDTO? {
        // Check if wallet exist
        if(!walletRepo.existsById(walletId)) throw NotFoundException("Wallet not found")

        // Get transaction
        val transaction = transactionRepo.findByIdOrNull(transactionId)?.toTransactionDTO()
            ?: throw NotFoundException("Transaction not found")

        // Check if transaction is related to the wallet
        if(walletId != transaction.beneficiaryWalletID && walletId != transaction.payerWalletID)
            throw ForbiddenException("Access not allowed")

        return transaction
    }
}