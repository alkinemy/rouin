package al.rouin.ledger.transaction.client

import al.rouin.common.RouinRepository
import al.rouin.user.UserId
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionRepository : RouinRepository<TransactionEntity, Long> {
    fun findByUserIdAndDeletedFalse(userId: UserId): List<TransactionEntity>
    fun findByUserIdAndDateGreaterThanEqualAndDateLessThanEqualAndDeletedFalse(userId: UserId, from: LocalDateTime, to: LocalDateTime): List<TransactionEntity>
    fun findByUserIdAndDeletedFalseOrderByIdDesc(userId: UserId): TransactionEntity?
}
