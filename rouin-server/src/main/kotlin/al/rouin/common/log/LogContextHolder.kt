package al.rouin.common.log

import org.springframework.core.NamedThreadLocal
import org.springframework.stereotype.Component

@Component
class LogContextHolder {
    private val threadLocal: ThreadLocal<LogContext> = NamedThreadLocal("log-context")

    fun set(context: LogContext) = threadLocal.set(context)

    fun get(): LogContext? = threadLocal.get()

    fun clear() = threadLocal.remove()
}