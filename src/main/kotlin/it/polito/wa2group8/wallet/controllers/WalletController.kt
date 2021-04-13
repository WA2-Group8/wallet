package it.polito.wa2group8.wallet.controllers

import it.polito.wa2group8.wallet.dto.TransactionDTO
import it.polito.wa2group8.wallet.dto.WalletDTO
import it.polito.wa2group8.wallet.services.WalletService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class WalletController(val walletService: WalletService)
{
    @PostMapping(value = ["/wallet"])
    @ResponseBody
    fun createWallet(
        @RequestBody customerId: Long
    ): WalletDTO?
    {
        return walletService.createWallet(customerId)
    }

    @GetMapping(value = ["/wallets/{id}/transactions"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getWalletTransactionsById(@PathVariable("id") wallet_id: Long): List<TransactionDTO>
    {
       // val transactionList: List<TransactionDTO> = walletService.getTransactionsByWalletId(wallet_id)
        TODO("Not yet implemented")
    }

    /*
    TODO("Define a model fora transaction body")
    @PostMapping(value = ["/wallet/{id}/transactions"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createTransaction(@PathVariable("id") wallet_id: Long) : TransactionDTO?
    {
        val transactionList: List<TransactionDTO> = transactionService.createTransaction(wallet_id)
        TODO("Not yet implemented")
    }
    */

    @PostMapping(value = ["/wallet/{walletId}/transaction"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createTransaction(
        @PathVariable("walletId") payer_wallet_id: Long,
        @RequestBody transaction: TransactionDTO
    ): TransactionDTO? {
        // TODO(Handle exceptions)
        return walletService.createTransaction(payer_wallet_id, transaction)
    }

    @GetMapping(value = ["/wallets/{walletId}/transactions/{transactionId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getTransactionById(
        @PathVariable("walletId") walletId: Long,
        @PathVariable("transactionId") transactionId: Long): ResponseEntity<String>
    {
        // TODO(Handle exception)
        val transactionRetrieved = walletService.getTransactionById(walletId, transactionId)

        return if (transactionRetrieved != null)
            ResponseEntity.ok()
                .header("Header AAA", "Test header")
                .body(transactionRetrieved.toString())
        else
            ResponseEntity.noContent().build()
    }
}
