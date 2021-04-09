package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.dto.TransactionDTO
import it.polito.wa2group8.wallet.dto.toTransactionDTO
import it.polito.wa2group8.wallet.dto.toTransactionEntity
import it.polito.wa2group8.wallet.repositories.TransactionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class WalletServiceImpl(
    val transactionRepo: TransactionRepository
//    val walletRepo: WalletRepository
//    val customerRepo: CustomerRepository
) : WalletService {

//    override fun createWallet (wallet: WalletDT0) {}

//    override fun getWalletById(id: Long) : WalletDTO? {}

    override fun createTransaction(transaction: TransactionDTO) {
        // controllare che i wallet esistano
        transactionRepo.save(transaction.toTransactionEntity())
    }

    override fun getTransactionsByWalletId(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime) : List<TransactionDTO> {
//        return transactionRepo.findByWalletAndTimeInstantBetween(walletId, endDate, startDate).map { t -> t.toTransactionDTO() }
        return emptyList()
    }

    override fun getTransactionById(transactionId: Long) : TransactionDTO? {
        return transactionRepo.findByIdOrNull(transactionId)?.toTransactionDTO()
    }
}