package it.polito.wa2group8.wallet.controllers

import it.polito.wa2group8.wallet.dto.RegistrationRequestDTO
import it.polito.wa2group8.wallet.dto.SignInBody
import it.polito.wa2group8.wallet.security.JwtUtils
import it.polito.wa2group8.wallet.services.UserDetailsService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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

        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userInfo.username,userInfo.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal
        return ResponseEntity.ok().header("jwt",jwt).body(userDetails)

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
        return ResponseEntity.status(201).body(userDetailsService.createUser(registrationRequest))


    }

    @GetMapping(value=["/auth/registrationConfirm"], produces=[MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun registrationConfirm(
        @RequestParam token : String
    ): ResponseEntity<Any> {
        return ResponseEntity.status(201).body(userDetailsService.confirmRegistration(token))

    }

    //------------------- ENDPOINTS RESERVED TO ADMINS -------------------

    @GetMapping(value = ["/enable/{username}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    fun enableUserByUsername(
        @PathVariable("username") username: String,
    ): ResponseEntity<Any>
    {
            //Only admins can enable an user manually
        return ResponseEntity.status(200).body(userDetailsService.enableUser(username))
    }
    @GetMapping(value = ["/disable/{username}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    fun disableUserByUsername(
        @PathVariable("username") username: String,
    ): ResponseEntity<Any>
    {
            //Only admins can disable an user manually
            return ResponseEntity.status(200).body(userDetailsService.disableUser(username))
    }
}