package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.dto.TransactionDTO
import it.polito.wa2group8.wallet.repositories.TransactionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TransactionServiceImp(val transactionRepository: TransactionRepository) : TransactionService
{
    override fun createTransaction(payerWalletID: Long, beneficiaryWallet: Long, amount: Double, transactionType: String): TransactionDTO
    {
        TODO("Not yet implemented")
    }

    override fun getTransactionById(transactionID: Long): TransactionDTO?
    {
        TODO("Not yet implemented")
    }

    override fun getTransactionsByWalletId(walletId: Long): List<TransactionDTO>
    {
        TODO("Not yet implemented")
    }

    override fun getTransactionsByWalletIdAndTimeFrame(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<TransactionDTO>
    {
        TODO("Not yet implemented")
    }
}
