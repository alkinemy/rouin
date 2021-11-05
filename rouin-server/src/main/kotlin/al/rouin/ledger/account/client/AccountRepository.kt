package al.rouin.ledger.account.client

import al.rouin.common.RouinRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : RouinRepository<AccountEntity, Long> {
    fun findByUserIdAndDeletedFalse(userId: String): List<AccountEntity>
}
