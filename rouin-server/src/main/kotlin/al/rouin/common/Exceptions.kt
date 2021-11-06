package al.rouin.common


open class RouinException : RuntimeException {
    open val errorCode: ErrorCode = ErrorCode.INTERNAL_SERVER_ERROR

    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}


class UserNotFoundException : RouinException {
    override val errorCode: ErrorCode = ErrorCode.USER_NOT_FOUND

    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}


class BudgetNotFoundException : RouinException {
    override val errorCode: ErrorCode = ErrorCode.BUDGET_NOT_FOUND

    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}