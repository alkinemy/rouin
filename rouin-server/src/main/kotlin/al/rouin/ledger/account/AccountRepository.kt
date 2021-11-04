package al.rouin.ledger.account

import al.rouin.common.RouinRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : RouinRepository<AccountEntity, Long> {
    fun findByUserId(userId: String): List<AccountEntity>
}
