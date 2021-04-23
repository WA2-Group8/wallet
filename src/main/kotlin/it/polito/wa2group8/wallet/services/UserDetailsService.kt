package it.polito.wa2group8.wallet.services

import it.polito.wa2group8.wallet.dto.SignInBody
import it.polito.wa2group8.wallet.dto.UserDetailsDTO

interface UserDetailsService {
    fun createUser(userDetails: UserDetailsDTO): UserDetailsDTO?
    fun addRoleToUser(role: String, username: String)
    fun removeRoleToUser(role: String, username: String)
    fun enableUser(username: String)
    fun disableUser(username: String)
    fun loadUserByUsername(username: String) : UserDetailsDTO
    fun doLogin(info: SignInBody)
    fun confirmRegistration(token: String) : String
}