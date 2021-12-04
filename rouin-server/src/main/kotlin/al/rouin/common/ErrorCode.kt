package al.rouin.common

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus
) {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND),
    BUDGET_NOT_FOUND(HttpStatus.NOT_FOUND),

    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR)
}