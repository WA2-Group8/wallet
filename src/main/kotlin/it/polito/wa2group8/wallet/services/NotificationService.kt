package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.User

interface NotificationService {
    fun createEmailVerificationToken(user: User) : String
}