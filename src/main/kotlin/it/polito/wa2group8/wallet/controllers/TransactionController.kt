package it.polito.wa2group8.wallet.controllers

import it.polito.wa2group8.wallet.dto.TransactionDTO
import it.polito.wa2group8.wallet.services.TransactionService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*


@RestController
class TransactionController(val transactionService: TransactionService)
{
    @GetMapping(value = ["/wallets/{id}/transactions"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getWalletTransactionsById(@PathVariable("id") wallet_id: Long): List<TransactionDTO>
    {
        //val transactionList: List<TransactionDTO> = transactionService.getTransactionsByWalletId(wallet_id)
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
}
