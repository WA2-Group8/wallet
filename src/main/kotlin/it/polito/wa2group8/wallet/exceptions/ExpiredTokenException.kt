package it.polito.wa2group8.wallet.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
class ExpiredTokenException(message: String?) : RuntimeException(message)