package org.cirgle.catalog.presenter.advice

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import org.cirgle.catalog.domain.exception.*
import org.cirgle.catalog.domain.exception.code.ErrorCode
import org.cirgle.catalog.presenter.dto.response.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.format.DateTimeParseException

@RestControllerAdvice
class APIExceptionHandler {

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleInvalidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorCodes = exception.bindingResult.fieldErrors.map {
            ErrorCode.valueOf(it.defaultMessage)
        }.sortedBy { errorCode ->
            errorCode.codeValue()
        }
        val errorCode = errorCodes.first()
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(errorCode)
            )
    }

    @ExceptionHandler(value = [SignatureException::class])
    fun handleSignatureException(exception: SignatureException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(ErrorCode.INVALID_TOKEN)
            )
    }

    @ExceptionHandler(value = [ExpiredJwtException::class])
    fun handleExpiredJwtException(exception: ExpiredJwtException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(ErrorCode.INVALID_TOKEN)
            )
    }

    @ExceptionHandler(value = [DateTimeParseException::class])
    fun handleDateTimeParseException(exception: DateTimeParseException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(ErrorCode.INVALID_DATE)
            )
    }

    @ExceptionHandler(
        value = [
            HttpMediaTypeNotSupportedException::class,
            HttpMessageNotReadableException::class,
        ]
    )
    fun handleHttpMediaTypeNotSupportedException(exception: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(ErrorCode.INVALID)
            )
    }

    @ExceptionHandler(value = [UserPasswordMismatchException::class])
    fun handleUserPasswordNotMatchException(exception: UserPasswordMismatchException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [UserNotFoundException::class])
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    fun handleUserAlreadyExistsException(exception: UserAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [MenuNotFoundException::class])
    fun handleMenuNotFoundException(exception: MenuNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [MenuAlreadyExistsException::class])
    fun handleMenuAlreadyExistsException(exception: MenuAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [MenuNameException::class])
    fun handleMenuNameException(exception: MenuNameException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [MenuCaffeineException::class])
    fun handleMenuCaffeineException(exception: MenuCaffeineException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [FriendAlreadyExistsException::class])
    fun handleFriendAlreadyExistsException(exception: FriendAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [FriendNotFoundException::class])
    fun handleFriendNotFoundException(exception: FriendNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [InvalidException::class])
    fun handleInvalidException(exception: InvalidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(exception.errorCode)
            )
    }

    @ExceptionHandler(value = [Exception::class])
    fun unhandledException(exception: Exception): ResponseEntity<ErrorResponse> {
        exception.printStackTrace()
        return ResponseEntity
            .badRequest()
            .body(
                errorBody(ErrorCode.UNKNOWN_ERROR)
            )
    }

    private fun ErrorCode.codeValue(): Int {
        return this.code.split("-")[1].toInt()
    }

    protected fun errorBody(errorCode: ErrorCode) = ErrorResponse(
        code = errorCode.code,
        message = errorCode.message,
    )
}