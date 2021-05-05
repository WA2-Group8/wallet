package it.polito.wa2group8.wallet.controllers

import it.polito.wa2group8.wallet.dto.CustomerDTO
import it.polito.wa2group8.wallet.dto.TransactionDTO
import it.polito.wa2group8.wallet.services.WalletService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class WalletController(val walletService: WalletService)
{
    @PostMapping(value = ["/wallet"], produces=[MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createWallet(
        @RequestBody @Valid customer: CustomerDTO,
        bindingResult: BindingResult
    ): ResponseEntity<Any>
    {
        if(bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getFieldError("customerId")?.defaultMessage)

        return ResponseEntity.status(201).body(walletService.createWallet(customer.customerId ?:-1))

    }

    @GetMapping(value = ["/wallet/{walletId}"], produces=[MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getWalletById(
        @PathVariable("walletId") walletId: Long
    ): ResponseEntity<Any>
    {
        return ResponseEntity.ok().body(walletService.getWalletById(walletId))

    }

    @PostMapping(value = ["/wallet/{walletId}/transaction"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createTransaction(
        @PathVariable("walletId") payer_wallet_id: Long,
        @RequestBody @Valid transaction: TransactionDTO,
        bindingResult: BindingResult
    ): ResponseEntity<Any>
    {
        if(bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getFieldError("amount")?.defaultMessage)

        return ResponseEntity.status(201).body(walletService.createTransaction(payer_wallet_id, transaction))

    }

    @GetMapping(value = ["/wallet/{id}/transactions"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getWalletTransactionsById(
        @PathVariable("id") wallet_id: Long,
        @RequestParam("from", required= true) from: Long,
        @RequestParam("to",required = true) to: Long
    ): ResponseEntity<Any>
    {
        return ResponseEntity.ok().body(walletService.getTransactionsByWalletId(wallet_id,from,to))
    }

    @GetMapping(value = ["/wallet/{walletId}/transactions/{transactionId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getTransactionById(
        @PathVariable("walletId") walletId: Long,
        @PathVariable("transactionId") transactionId: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok().body(walletService.getTransactionById(walletId, transactionId))
    }

}
