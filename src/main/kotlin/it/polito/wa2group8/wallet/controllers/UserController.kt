package it.polito.wa2group8.wallet.controllers

import it.polito.wa2group8.wallet.dto.RegistrationRequestDTO
import it.polito.wa2group8.wallet.dto.SignInBody
import it.polito.wa2group8.wallet.exceptions.BadRequestException
import it.polito.wa2group8.wallet.exceptions.ExpiredTokenException
import it.polito.wa2group8.wallet.exceptions.InvalidAuthException
import it.polito.wa2group8.wallet.exceptions.NotFoundException
import it.polito.wa2group8.wallet.security.JwtUtils
import it.polito.wa2group8.wallet.services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
class UserController(val userDetailsService: UserDetailsService, val authenticationManager: AuthenticationManager, val jwtUtils: JwtUtils)
{



    @GetMapping("/")
    fun root(principal: Principal?) = "Hello, ${principal?.name ?: "guest"}"

    @PostMapping(value = ["/auth/signin"], produces=[MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun login(
        @RequestBody @Valid userInfo: SignInBody,
        bindingResult: BindingResult
    ): ResponseEntity<Any>
    {
        if (bindingResult.hasErrors())
            return  ResponseEntity.status(401).body("Incorrect username and/or password")
        return try
        {
            val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userInfo.username,userInfo.password))
            SecurityContextHolder.getContext().authentication = authentication
            println("TUTTO OK")
            val jwt = jwtUtils.generateJwtToken(authentication)
            val userDetails = authentication.principal
            return ResponseEntity.ok().header("jwt",jwt).body(userDetails)

            //userDetailsService.doLogin(userInfo)
            //ResponseEntity.ok().body("ok")
        }
        catch (ex: InvalidAuthException)
        {
            ResponseEntity.status(401).body(ex.message)
        }
    }

    @PostMapping(value=["/auth/register"], produces=[MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun register(
        @RequestBody @Valid registrationRequest: RegistrationRequestDTO,
        bindingResult: BindingResult
    ): ResponseEntity<Any> {
        // Check validation results
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(
                bindingResult.fieldErrors.map { f -> f.field + " " + f.defaultMessage })
        // Check password
        if (registrationRequest.password != registrationRequest.confirmPassword)
            return ResponseEntity.badRequest().body("Password and confirmPassword do not match")
        // Add new user
        return try {
            ResponseEntity.status(201).body(userDetailsService.createUser(registrationRequest))
        } catch (ex: BadRequestException) {
            ResponseEntity.badRequest().body(ex.message)
        }
    }

    @GetMapping(value=["/auth/registrationConfirm"], produces=[MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun registrationConfirm(
        @RequestParam token : String
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(201).body(userDetailsService.confirmRegistration(token))
        } catch (ex: NotFoundException) {
            ResponseEntity.status(404).body(ex.message)
        } catch (ex: ExpiredTokenException) {
            ResponseEntity.status(408).body(ex.message)
        }
    }

    //------------------- ENDPOINTS RESERVED TO ADMINS -------------------

    @GetMapping(value = ["/enable/{username}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun enableUserByUsername(
        @PathVariable("username") username: String,
    ): ResponseEntity<Any>
    {
        val userDetail = SecurityContextHolder.getContext().authentication.principal
        println(SecurityContextHolder.getContext().authentication)
        if (userDetail == "ADMIN")
        {
            //Only admins can enable an user manually
            return try
            {
                ResponseEntity.status(201).body(userDetailsService.enableUser(username))
            }
            catch (ex: NotFoundException)
            {
                ResponseEntity.status(404).body(ex.message)
            }
        }
        //Unauthorized: user is not an admin!
        return ResponseEntity.status(401).body("UNAUTHORIZED")
    }
    @GetMapping(value = ["/disable/{username}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun disableUserByUsername(
        @PathVariable("username") username: String,
    ): ResponseEntity<Any>
    {
        val userDetail = SecurityContextHolder.getContext().authentication.principal
        if (userDetail == "ADMIN")
        {
            //Only admins can disable an user manually
            return try
            {
                ResponseEntity.status(201).body(userDetailsService.disableUser(username))
            }
            catch (ex: NotFoundException)
            {
                ResponseEntity.status(404).body(ex.message)
            }
        }
        //Unauthorized: user is not an admin!
        return ResponseEntity.status(401).body("UNAUTHORIZED")
    }
}