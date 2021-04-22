package it.polito.wa2group8.wallet.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MailServiceImpl : MailService {

    @Autowired
    private lateinit var mailSender : JavaMailSender

    override fun sendMessage(toMail:String, subject: String, mailBody: String) {
        // Create a message
        val message = SimpleMailMessage()
        message.setTo(toMail)
        message.setSubject(subject)
        message.setText(mailBody)
        // Send the message
        mailSender.send(message)
    }
}