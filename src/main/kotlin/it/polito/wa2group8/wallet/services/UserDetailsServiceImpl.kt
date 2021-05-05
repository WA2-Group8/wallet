package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.Customer
import it.polito.wa2group8.wallet.domain.User
import it.polito.wa2group8.wallet.dto.RegistrationRequestDTO
import it.polito.wa2group8.wallet.dto.SignInBody
import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import it.polito.wa2group8.wallet.dto.toUserDetailsDTO
import it.polito.wa2group8.wallet.exceptions.BadRequestException
import it.polito.wa2group8.wallet.exceptions.ExpiredTokenException
import it.polito.wa2group8.wallet.exceptions.InvalidAuthException
import it.polito.wa2group8.wallet.exceptions.NotFoundException
import it.polito.wa2group8.wallet.repositories.CustomerRepository
import it.polito.wa2group8.wallet.repositories.EmailVerificationTokenRepository
import it.polito.wa2group8.wallet.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.InetAddress
import java.sql.Timestamp


@Service
@Transactional
class UserDetailsServiceImpl(
    val userRepository: UserRepository,
    val customerRepository: CustomerRepository,
    val emailVerificationTokenRepository: EmailVerificationTokenRepository,
    val mailService: MailService,
    val notificationService: NotificationService
): UserDetailsService {

    @Autowired
    private val webServerAppContext: ServletWebServerApplicationContext? = null


    val encoder = BCryptPasswordEncoder()


    override fun createUser(registrationRequest: RegistrationRequestDTO): UserDetailsDTO? {
        // Check if username is already in the DB
        if (userRepository.findByUsername(registrationRequest.username) != null)
            throw BadRequestException("Username already exist")
        // Save user in the DB
        val user = userRepository.save(User(registrationRequest.username, encoder.encode(registrationRequest.password), registrationRequest.email, roles="CUSTOMER"))
        val customer = Customer(null, null, null, user)
        customerRepository.save(customer)

        // Create email message
        val token = notificationService.createEmailVerificationToken(user)
        val addr = InetAddress.getLocalHost().hostAddress
        val port = webServerAppContext?.webServer?.port
        val text = "http://$addr:$port/auth/registrationConfirm?token=$token"
        // Send email
        mailService.sendMessage(user.email, "Confirm your registration", text)
        return user.toUserDetailsDTO()
    }

    override fun addRoleToUser(role: String, username: String) {
        val user = userRepository.findByUsername(username) ?: throw BadRequestException("Username does not exist")
        try {
            user.addRolename(role)
            userRepository.save(user)
            if (role == "CUSTOMER")
            {
                val customer = Customer(null, null, null, user)
                customerRepository.save(customer)
            }
        }
        catch (ex: Exception) {
            throw ex
        }
    }

    override fun removeRoleToUser(role: String, username: String) {
        val user = userRepository.findByUsername(username) ?: throw BadRequestException("Username does not exist")
        try {
            user.removeRolename(role)
            userRepository.save(user)
            if (role == "CUSTOMER")
            {
                customerRepository.deleteCustomerByUser(user)
            }
        }
        catch (ex: Exception) {
            throw ex
        }
    }

    override fun enableUser(username: String) {
        if(userRepository.findByUsername(username) == null) throw BadRequestException("Username does not exist")
        userRepository.enableUser(username)
    }

    override fun disableUser(username: String) {
        if(userRepository.findByUsername(username) == null) throw BadRequestException("Username does not exist")
        userRepository.disableUser(username)
    }

    override fun loadUserByUsername(username: String): UserDetailsDTO {
        val user = userRepository.findByUsername(username) ?: throw BadRequestException("Username does not exist")
        return user.toUserDetailsDTO()
    }

    override fun confirmRegistration(token: String): String {
        val userToken = emailVerificationTokenRepository.findEmailVerificationTokenByToken(token)
            ?: throw NotFoundException("Token not valid.")
        if(userToken.expiryDate < Timestamp(System.currentTimeMillis())) throw ExpiredTokenException("The token is expired.")
        val user = userRepository.findByUsername(userToken.username)
            ?: throw NotFoundException("User not found.")
        if(user.isEnabled) return "User is already registered."
        user.isEnabled = true
        userRepository.save(user)
        return "Your registration is completed successfully."
    }

}