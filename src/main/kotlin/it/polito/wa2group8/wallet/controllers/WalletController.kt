package it.polito.wa2group8.wallet.controllers

import it.polito.wa2group8.wallet.dto.TransactionDTO
import it.polito.wa2group8.wallet.dto.WalletDTO
import it.polito.wa2group8.wallet.exceptions.BadRequestException
import it.polito.wa2group8.wallet.exceptions.NotFoundException
import it.polito.wa2group8.wallet.services.WalletService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest


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

    @GetMapping(value = ["/wallet/{walletId}"])
    @ResponseBody
    fun getWalletById(){
        // TODO()
    }

    @PostMapping(value = ["/wallet/{walletId}/transaction"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createTransaction(
        @PathVariable("walletId") payer_wallet_id: Long,
        @RequestBody transaction: TransactionDTO
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(201).body(walletService.createTransaction(payer_wallet_id, transaction))
        } catch (ex: BadRequestException) {
            ResponseEntity.badRequest().body(ex.message)
        } catch (ex: NotFoundException) {
            ResponseEntity.status(404).body(ex.message)
        }
    }

    @GetMapping(value = ["/wallets/{id}/transactions"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getWalletTransactionsById(@PathVariable("id") wallet_id: Long): List<TransactionDTO>
    {
        // val transactionList: List<TransactionDTO> = walletService.getTransactionsByWalletId(wallet_id)
        TODO("Not yet implemented with dates")
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
