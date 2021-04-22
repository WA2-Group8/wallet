package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.User
import it.polito.wa2group8.wallet.dto.SignInBody
import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import it.polito.wa2group8.wallet.dto.toUserDetailsDTO
import it.polito.wa2group8.wallet.exceptions.BadRequestException
import it.polito.wa2group8.wallet.exceptions.InvalidAuthException
import it.polito.wa2group8.wallet.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.InetAddress


@Service
@Transactional
class UserDetailsServiceImpl(
    val userRepository: UserRepository,
    val mailService: MailService,
    val notificationService: NotificationService
): UserDetailsService {

    @Autowired
    private val webServerAppContext: ServletWebServerApplicationContext? = null

    override fun createUser(userDetails: UserDetailsDTO): UserDetailsDTO? {
        // Check if username is already in the DB
        if(userRepository.findByUsername(userDetails.username) != null)
            throw BadRequestException("Username already exist")
        // Save user in the DB
        val user = userRepository.save(User(null, userDetails.username, userDetails.password!!, userDetails.email, roles="CUSTOMER"))
        // Create email message
        val token = notificationService.createEmailVerificationToken(user)
        val hostname = InetAddress.getLocalHost().hostAddress
        val port = webServerAppContext?.webServer?.port
        val text = "http://$hostname:$port/auth/registrationConfirm?token=$token"
        // Send email
        mailService.sendMessage(user.email, "Confirm your registration", text)
        return user.toUserDetailsDTO()
    }

    override fun addRoleToUser(role: String, username: String) {
        //userRepository.findByUsername(user.username!!) ?: throw RuntimeException("Customer not found")
    }

    override fun removeRoleToUser(role: String, username: String) {
        TODO("Not yet implemented")
    }

    override fun enableUser(username: String) {
        TODO("Not yet implemented")
    }

    override fun disableUser(username: String) {
        TODO("Not yet implemented")
    }

    override fun loadUserByUsername(username: String): UserDetailsDTO {
        TODO("Not yet implemented")
    }

    override fun doLogin(info: SignInBody)
    {
        //TODO("Compute password hash")
        val user = userRepository.findByUsername(info.username)
        if (user == null || user.password != info.password)
            throw InvalidAuthException("Incorrect username and/or password")
        //If I'm here, username and password are both valid.
    }
}