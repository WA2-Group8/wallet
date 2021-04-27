package it.polito.wa2group8.wallet.domain

import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty



@Entity
@Table(indexes = [Index(name="usernameIndex", columnList = "username", unique=true)])
class User (
    @Column(name="username", unique=true)
    @get:NotEmpty(message="Username must be not empty")
    @Valid
    var username: String,

    @Column(nullable=false)
    var password: String,

    @Column(unique=true)
    @get:Email
    @Valid
    var email: String,

    //TODO Controlla
    var isEnabled: Boolean = false,

    var roles: String
): EntityBase<Long>()
{
    enum class Rolename {
        CUSTOMER,
        ADMIN
    }

    private inline fun <reified T : Enum<T>> enumContains(name: String): Boolean {
        return enumValues<T>().any { it.name == name}
    }

    fun getRolenames(): Set<String>{
        val set: MutableSet<String> = mutableSetOf()
        set.addAll(roles.split(','))
        return set
    }

    fun addRolename(role: String){
        if(!enumContains<Rolename>(role)) throw RuntimeException("Rolename not found")
        if(roles.split(',').contains(role)) throw RuntimeException("Rolename already exist")
        roles=if(roles.isEmpty()) role else "$roles,$role"
    }

    fun removeRolename(role: String){
        if(!enumContains<Rolename>(role)) throw RuntimeException("Rolename not found")
        val rolesSet = getRolenames()
        if (!rolesSet.contains(role)) throw RuntimeException("Role not present")
        roles = rolesSet.filter { it!=role }.joinToString(",")

    }
}