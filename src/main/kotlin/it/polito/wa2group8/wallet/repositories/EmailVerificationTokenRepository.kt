package it.polito.wa2group8.wallet.repositories

import it.polito.wa2group8.wallet.domain.EmailVerificationToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailVerificationTokenRepository: CrudRepository<EmailVerificationToken, Long> {
    fun findEmailVerificationTokenByToken(token: String) : EmailVerificationToken?
}