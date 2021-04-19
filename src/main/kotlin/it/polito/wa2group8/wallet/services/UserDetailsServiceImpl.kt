package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.domain.User
import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import it.polito.wa2group8.wallet.dto.toUserDetailsDTO
import it.polito.wa2group8.wallet.exceptions.NotFoundException
import it.polito.wa2group8.wallet.repositories.UserRepository
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException
import javax.persistence.PersistenceException

@Service
@Transactional
class UserDetailsServiceImpl(
    val userRepository: UserRepository
): UserDetailsService {
    override fun createUser(user: UserDetailsDTO): UserDetailsDTO? {
        //userRepository.findByUsername(user.username!!) ?: throw RuntimeException("username already exist")

            try{
                val user = User(null, user.username!!, user.password!!, user.email, roles = user.roles)
                return userRepository.save(user).toUserDetailsDTO()
            } catch (ex: Exception){
                println("qui")
                throw RuntimeException("Username already exist")
            }

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

}