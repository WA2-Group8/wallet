package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.Rolename
import it.polito.wa2group8.wallet.domain.User
import it.polito.wa2group8.wallet.domain.enumContains
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

        val roles = userDetails.roles?.split(',') ?: throw BadRequestException("Roles not valid")
        roles.forEach{if(!enumContains<Rolename>(it)) throw throw BadRequestException("Roles not valid")}

        val user = User(null, userDetails.username, userDetails.password!!, userDetails.email, roles=userDetails.roles!!)
        return userRepository.save(user).toUserDetailsDTO()
    }

    override fun addRoleToUser(role: String, username: String) {
        val user = userRepository.findByUsername(username) ?: throw BadRequestException("Username does not exist")
        user.addRolename(role)
    }

    override fun removeRoleToUser(role: String, username: String) {
        val user = userRepository.findByUsername(username) ?: throw BadRequestException("Username does not exist")
        user.removeRolename(role)
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

    override fun doLogin(info: SignInBody)
    {
        //TODO("Compute password hash")
        val user = userRepository.findByUsername(info.username)
        if (user == null || user.password != info.password)
            throw InvalidAuthException("Incorrect username and/or password")
        //If I'm here, username and password are both valid.
    }
}