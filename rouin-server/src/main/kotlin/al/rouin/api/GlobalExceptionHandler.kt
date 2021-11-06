package al.rouin.api

import al.rouin.common.Constants.EMPTY_STRING
import al.rouin.common.ErrorCode
import al.rouin.common.RouinException
import al.rouin.common.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    private val log = this.logger()

    companion object {
        private val DEFAULT_ERROR_CODE = ErrorCode.INTERNAL_SERVER_ERROR
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiError> {
        log.error("[EXCEPTION] Fail to handle the request", e)
        return ResponseEntity(
            ApiError(
                errorCode = DEFAULT_ERROR_CODE,
                errorMessage = e.message ?: EMPTY_STRING
            ),
            DEFAULT_ERROR_CODE.status
        )
    }

    @ExceptionHandler(RouinException::class)
    fun handleRouinException(e: RouinException): ResponseEntity<ApiError> {
        log.error("[EXCEPTION] Fail to handle the request", e)
        return ResponseEntity(
            ApiError(
                errorCode = e.errorCode,
                errorMessage = e.message ?: EMPTY_STRING
            ),
            e.errorCode.status
        )
    }
}


data class ApiError(
    val date: LocalDateTime = LocalDateTime.now(),
    val errorCode: ErrorCode,
    val errorMessage: String,
)