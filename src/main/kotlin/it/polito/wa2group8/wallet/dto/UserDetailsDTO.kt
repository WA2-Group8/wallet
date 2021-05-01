package it.polito.wa2group8.wallet.dto

import it.polito.wa2group8.wallet.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size
import org.springframework.security.core.authority.SimpleGrantedAuthority

import java.util.ArrayList


data class SignInBody(
    @get:NotEmpty val username: String,
    @get:Size(min = 8) val password: String)



class UserDetailsDTO(
    private val username: String,
    private val password: String?,
    private val email: String?,
    private val isEnabled: Boolean?,
    //TODO("gli altri boolen vanno aggiunti?")
    private val roles: Set<String>,
): UserDetails
{
    override fun getPassword(): String = password ?: ""

    override fun getUsername(): String = username

    override fun isEnabled(): Boolean = isEnabled ?: false

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
    {
        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
        for (role in roles)
            authorities.add(SimpleGrantedAuthority(role))
        return authorities
    }

    fun getEmail(): String = email ?: ""

    //NON ABBIAMO QUESTE INFORMAZIONI NEL DB!!!!
    override fun isAccountNonExpired(): Boolean
    {
        return true
    }

    override fun isAccountNonLocked(): Boolean
    {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean
    {
        return true
    }
}

fun User.toUserDetailsDTO() = UserDetailsDTO(username, password, email, isEnabled, getRolenames())