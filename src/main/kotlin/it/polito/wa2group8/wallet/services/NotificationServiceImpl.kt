package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.EmailVerificationToken
import it.polito.wa2group8.wallet.domain.User
import it.polito.wa2group8.wallet.repositories.EmailVerificationTokenRepository
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*


@Service
@Transactional
@EnableScheduling
class NotificationServiceImpl(
    val emailVerificationTokenRepository: EmailVerificationTokenRepository
) : NotificationService{
    override fun createEmailVerificationToken(user: User) : String{
        val token = UUID.randomUUID().toString()
        val timestamp = Timestamp(System.currentTimeMillis()+5*60*1000)
        val emailVerificationToken = EmailVerificationToken( timestamp, token, user.username)
        emailVerificationTokenRepository.save(emailVerificationToken)
        return token
    }

    @Scheduled(fixedDelay = 60000)
    fun checkExpiration(){
        val expiredList = emailVerificationTokenRepository.findEmailVerificationTokensByExpiryDateIsBefore(Timestamp(System.currentTimeMillis()))
        println("Automatic delete expired token:\n ${expiredList.map{it.token}}")
        emailVerificationTokenRepository.deleteAll(expiredList)
    }
}