package it.polito.wa2group8.wallet

import it.polito.wa2group8.wallet.domain.Customer
import it.polito.wa2group8.wallet.domain.User
import it.polito.wa2group8.wallet.domain.Wallet
import it.polito.wa2group8.wallet.repositories.CustomerRepository
import it.polito.wa2group8.wallet.repositories.WalletRepository
import it.polito.wa2group8.wallet.services.UserDetailsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import it.polito.wa2group8.wallet.dto.RegistrationRequestDTO
import it.polito.wa2group8.wallet.repositories.UserRepository
import java.math.BigDecimal

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WalletApplication{
    @Bean
    fun test(walletRepository : WalletRepository,customerRepository : CustomerRepository, userDetailsService: UserDetailsService, userRepository: UserRepository) : CommandLineRunner{
        return CommandLineRunner{
            val encoder = BCryptPasswordEncoder()
            userDetailsService.createUser(RegistrationRequestDTO("Lorenzo","12345678","12345678","email1@email.com"))
            userDetailsService.createUser(RegistrationRequestDTO("Leonardo","12345678","12345678","email2@email.com"))
            userDetailsService.createUser(RegistrationRequestDTO("Maria","12345678","12345678","email3@email.com"))
            val u1 = userRepository.findByUsername("Lorenzo")
            u1!!.addRolename("ADMIN")
            val banca = Customer("Banca","DeiPoveri","ViaZero",u1)
            val w1= Wallet(banca, BigDecimal(10000))
        }
    }
}

@Value("\${spring.mail.host}")
private val host : String = ""
@Value("\${spring.mail.port}")
private val port: Int = 0
@Value("\${spring.mail.username}")
private val username: String = ""
@Value("\${spring.mail.password}")
private val password: String = ""
@Value("\${spring.mail.properties.mail.smtp.auth}")
private val auth: Boolean = true
@Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
private val starttls: Boolean = true
@Value("\${spring.mail.properties.mail.debug}")
private val debug: Boolean = true

@Bean
fun getMailSender() : JavaMailSender {
    // Setting Mail Sender
    val mailSender = JavaMailSenderImpl()
    mailSender.host = host
    mailSender.port = port
    mailSender.username = username
    mailSender.password = password
    // Setting properties
    val properties = mailSender.javaMailProperties
    properties["spring.mail.properties.mail.smtp.auth"] = auth
    properties["spring.mail.properties.mail.smtp.starttls.enable"] = starttls
    properties["spring.mail.properties.mail.debug"] = debug
    // Returning Mail Sender
    return mailSender
}



fun main(args: Array<String>) {
    runApplication<WalletApplication>(*args)
}