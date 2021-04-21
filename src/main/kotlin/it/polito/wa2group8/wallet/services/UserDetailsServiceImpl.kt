package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.User
import it.polito.wa2group8.wallet.dto.SignInBody
import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import it.polito.wa2group8.wallet.dto.toUserDetailsDTO
import it.polito.wa2group8.wallet.exceptions.BadRequestException
import it.polito.wa2group8.wallet.exceptions.InvalidAuthException
import it.polito.wa2group8.wallet.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserDetailsServiceImpl(
    val userRepository: UserRepository
): UserDetailsService {
    override fun createUser(userDetails: UserDetailsDTO): UserDetailsDTO? {
        if(userRepository.findByUsername(userDetails.username) != null)
            throw BadRequestException("Username already exist")

        val user = User(null, userDetails.username, userDetails.password!!, userDetails.email, roles="CUSTOMER")
        return userRepository.save(user).toUserDetailsDTO()
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