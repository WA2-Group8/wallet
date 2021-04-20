package it.polito.wa2group8.wallet.controllers

import it.polito.wa2group8.wallet.dto.SignInBody
import it.polito.wa2group8.wallet.exceptions.InvalidAuthException
import it.polito.wa2group8.wallet.services.UserDetailsServiceImpl
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
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
}