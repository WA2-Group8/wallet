package it.polito.wa2group8.wallet.services

interface MailService {
    fun sendMessage(toMail:String, subject: String, mailBody: String)
}