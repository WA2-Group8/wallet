package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.EmailVerificationToken
import it.polito.wa2group8.wallet.domain.User
import it.polito.wa2group8.wallet.repositories.EmailVerificationTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*


@Service
@Transactional
class NotificationServiceImpl(
    val emailVerificationTokenRepository: EmailVerificationTokenRepository
) : NotificationService{
    override fun createEmailVerificationToken(user: User) : String{
        val token = UUID.randomUUID().toString()
        val timestamp = Timestamp(System.currentTimeMillis())
        val emailVerificationToken = EmailVerificationToken(null, timestamp, token, user)
        emailVerificationTokenRepository.save(emailVerificationToken)
        return token
    }

}