package al.rouin.ledger.account.client

import al.rouin.common.RouinRepository
import al.rouin.ledger.account.AccountId
import al.rouin.user.UserId
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : RouinRepository<AccountEntity, Long> {
    fun findByUserIdAndDeletedFalse(userId: UserId): List<AccountEntity>
    fun findByUserIdAndAccountIdIsInAndDeletedFalse(userId: UserId, accountIds: Set<AccountId>): List<AccountEntity>
}
