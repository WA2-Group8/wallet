package it.polito.wa2group8.wallet.controllers

import it.polito.wa2group8.wallet.dto.SignInBody
import it.polito.wa2group8.wallet.dto.UserDetailsDTO
import it.polito.wa2group8.wallet.exceptions.BadRequestException
import it.polito.wa2group8.wallet.exceptions.InvalidAuthException
import it.polito.wa2group8.wallet.services.UserDetailsServiceImpl
import org.hibernate.exception.ConstraintViolationException
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class UserController(val userDetailsService: UserDetailsServiceImpl)
{
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
            userDetailsService.doLogin(userInfo)
            ResponseEntity.ok().body("ok")
        }
        catch (ex: InvalidAuthException)
        {
            ResponseEntity.status(401).body(ex.message)
        }
    }

    @PostMapping(value=["/auth/register"], produces=[MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun register(
        @RequestBody @Valid userDetails: UserDetailsDTO,
        bindingResult: BindingResult
    ): ResponseEntity<Any> {
        // Check validation results
        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(
                bindingResult.fieldErrors.map { f -> f.field + " " + f.defaultMessage })
        // Check password
        if(userDetails.password != userDetails.confirmPassword)
            return ResponseEntity.badRequest().body("Password and confirmPassword do not match")
        // Add new user
        return try {
            ResponseEntity.status(201).body(userDetailsService.createUser(userDetails))
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
            ResponseEntity.status(201).body("")
        } catch (ex: BadRequestException) {
            ResponseEntity.badRequest().body(ex.message)
        }
    }
}