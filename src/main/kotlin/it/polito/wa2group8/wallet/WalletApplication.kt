package it.polito.wa2group8.wallet

import it.polito.wa2group8.wallet.domain.Customer
import it.polito.wa2group8.wallet.domain.User
import it.polito.wa2group8.wallet.domain.Wallet
import it.polito.wa2group8.wallet.repositories.CustomerRepository
import it.polito.wa2group8.wallet.repositories.UserRepository
import it.polito.wa2group8.wallet.repositories.WalletRepository
import it.polito.wa2group8.wallet.services.UserDetailsService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.math.BigDecimal
import it.polito.wa2group8.wallet.dto.UserDetailsDTO

@SpringBootApplication
class WalletApplication{
    @Bean
    fun test(walletRepository : WalletRepository,customerRepository : CustomerRepository, userRepository: UserRepository,userService: UserDetailsService) : CommandLineRunner{
        return CommandLineRunner{
            /*val c1 = Customer(null,"Joe","Doe","joeDoe@email.com","ViaNumero3")
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
            walletRepository.save((bank))*/
            //val u1 = User(null,"","passwordsicurissima","m@v.it",roles="")
            //val u2 = User(null,"lorenzo","passwordsicurissima","ciao",roles="")
            //val u3 = User(null,"lorenz","passwordsicurissima","ciao@mail.it",roles="")
            val u4 = User(null,"lorenz","passwordsicurissima","ciao@mail.it",roles="")
            val u5 = User(null,"loren","passwordsicurissima","ciao@mail1.it",roles="CUSTOMER")
            val u6 = User(null,"lore","passwordsicurissima","ciao@mail2.it",roles="CUSTOMER,ADMIN")
            userRepository.saveAll(listOf(u4,u5,u6))
            /**
             * Debug userServiceImpl
             **/
            try {
                userService.createUser(UserDetailsDTO(null, "aldoBaglio", "abc", "abc", "aldo@email.com", "CUSTOMER")) //ok
                //userService.createUser(UserDetailsDTO(null, "aldoBaglio", "abc", "abc", "aldofake@email.com", "CUSTOMER")) //userame duplicated
                //userService.createUser(UserDetailsDTO(null, "aldoBaglio2", "abc", "abc", "aldo2@email.com", "TEACHER")) //role not valid
                userService.addRoleToUser("CUSTOMER","lorenz") //ok
                //userService.addRoleToUser("cc","lorenz") //role not valid
                //userService.addRoleToUser("CUSTOMER","notValid") //Username not valid
                userService.removeRoleToUser("CUSTOMER","lorenz") //ok
                //userService.removeRoleToUser("TEACHER","lorenz") //role not present
                //userService.removeRoleToUser("CUSTOMER","lo") //role not present
                userService.enableUser("lorenz") //ok
                //userService.enableUser("lor") // Username not found
                userService.disableUser("lorenz") //ok
                //userService.disableUser("lo") //username not valid
                println(userService.loadUserByUsername("lorenz")) //ok
                //println(userService.loadUserByUsername("lo")) //ok

            } catch(ex: Exception){
                println(ex.message)
            }
            /*val u = userRepository.findByUsername("loren")
            u?.addRolename("ADMIN")
            println(u?.getRolenames())
            u?.removeRolename("CUSTOMER")
            println(u?.getRolenames())*/
            //try {
                //userService.createUser(UserDetailsDTO(null, "lorenz", "klfdvk", "cioa@vv.com", "CUSTOMER"))
            /*} catch(ex: RuntimeException){
                println(ex.message)
            }*/

        }
    }
}

fun main(args: Array<String>) {
    runApplication<WalletApplication>(*args)

}
