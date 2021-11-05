package al.rouin.ledger.transaction.client

import al.rouin.common.RouinRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : RouinRepository<TransactionEntity, Long> {
    fun findByUserIdAndDeletedFalse(userId: String): List<TransactionEntity>
}
