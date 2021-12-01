package al.rouin.api

import al.rouin.common.log.LogContext
import al.rouin.common.log.LogContextHolder
import al.rouin.common.logger
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogInterceptor(
    private val logContextHolder: LogContextHolder,
) : HandlerInterceptor {

    private val log = this.logger()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestId = UUID.randomUUID().toString() //TODO receive request id from a client
        logContextHolder.set(LogContext(requestId = requestId, requestedAt = LocalDateTime.now()))
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        logContextHolder.get()
            ?.let {
                val completedAt = LocalDateTime.now()
                val requestTime = Duration.between(it.requestedAt, completedAt)
                log.debug("[API] (${it.requestId}) ${request.method} ${request.requestURI} ${requestTime.toMillis()}ms")
            }
        logContextHolder.clear()
    }
}