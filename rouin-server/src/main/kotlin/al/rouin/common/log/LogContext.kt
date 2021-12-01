package al.rouin.common.log

import java.time.LocalDateTime

data class LogContext(
    val requestId: String,
    val requestedAt: LocalDateTime,
)