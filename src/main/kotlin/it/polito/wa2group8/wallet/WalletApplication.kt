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
    fun test(walletRepository : WalletRepository,customerRepository : CustomerRepository) : CommandLineRunner{
        return CommandLineRunner{
            val c1 = Customer(null,"Joe","Doe","joeDoe@email.com","ViaNumero3")
            customerRepository.save(c1)
            val c2 = Customer(null,"John","Smith","johnSmith@email.com","ViaNumero0")
            customerRepository.save(c2)
            val c3 = Customer(null,"Gertrude","White","gertrudeWhite@email.com","ViaNumero1")
            customerRepository.save(c3)
            val c17 = Customer(null,"Androide","Diciasette","c17@email.com","ViaNumero17")
            customerRepository.save(c17)
            val cBank = Customer(null,"Banca", "DeiPoveri","truffa@email.com","Zurigo")
            customerRepository.save(cBank)
            val bank = Wallet(null,cBank,BigDecimal(1000000000.99))
            walletRepository.save((bank))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<WalletApplication>(*args)

}
