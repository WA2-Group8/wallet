package it.polito.wa2group8.wallet.controllers

import it.polito.wa2group8.wallet.exceptions.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler(){

    @ExceptionHandler(value=[BadRequestException::class])
    fun handleBadRequest(ex: BadRequestException, request: WebRequest): ResponseEntity<Any>{
        return ResponseEntity.badRequest().body(ex.message)
    }
    @ExceptionHandler(value=[ExpiredTokenException::class])
    fun handleExpiredToken(ex: ExpiredTokenException, request: WebRequest): ResponseEntity<Any>{
        return  ResponseEntity.status(408).body(ex.message)
    }
    @ExceptionHandler(value=[ForbiddenException::class])
    fun handleForbidden(ex: ForbiddenException, request: WebRequest): ResponseEntity<Any>{
        return ResponseEntity.status(403).body(ex.message)
    }
    @ExceptionHandler(value=[InvalidAuthException::class])
    fun handleInvalidAuth(ex: InvalidAuthException, request: WebRequest): ResponseEntity<Any>{
        return ResponseEntity.status(401).body(ex.message)
    }
    @ExceptionHandler(value=[NotFoundException::class])
    fun handleNotFound(ex: NotFoundException, request: WebRequest): ResponseEntity<Any>{
        return ResponseEntity.status(404).body(ex.message)
    }
}