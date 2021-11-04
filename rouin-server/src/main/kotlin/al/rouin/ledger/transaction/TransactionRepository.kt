package al.rouin.ledger.transaction

import al.rouin.common.RouinRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : RouinRepository<TransactionEntity, Long> {
    fun findByUserId(userId: String): List<TransactionEntity>
}
