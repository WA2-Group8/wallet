package it.polito.wa2group8.wallet

import it.polito.wa2group8.wallet.domain.Customer
import it.polito.wa2group8.wallet.domain.Wallet
import it.polito.wa2group8.wallet.repositories.CustomerRepository
import it.polito.wa2group8.wallet.repositories.WalletRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.math.BigDecimal

@SpringBootApplication
class WalletApplication{
    @Bean
    fun test(walletRepository : WalletRepository) : CommandLineRunner{
        return CommandLineRunner{
            val w1 = Wallet(null,1, -10)
            walletRepository.save(w1)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<WalletApplication>(*args)

}
