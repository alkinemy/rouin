package al.rouin.ledger.transaction.client

import al.rouin.common.RouinRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TransactionRepository : RouinRepository<TransactionEntity, Long> {
    fun findByUserIdAndDeletedFalse(userId: String): List<TransactionEntity>
    fun findByUserIdAndDateGreaterThanEqualAndDateLessThanEqualAndDeletedFalse(userId: String, from: LocalDate, to: LocalDate): List<TransactionEntity>
    fun findByUserIdAndDeletedFalseOrderByIdDesc(userId: String): TransactionEntity?
}
