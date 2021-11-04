package al.rouin.ledger.account

import al.rouin.external.ReferenceId
import al.rouin.user.User
import al.rouin.user.UserId
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val accountClient: AccountClient,
) {
    fun get(userId: UserId): List<Account> = accountRepository.findByUserIdAndDeletedFalse(userId.id)
        .map { it.toModel() }

    fun getByReferenceId(userId: UserId): Map<ReferenceId, Account> =
        accountRepository.findByUserIdAndDeletedFalse(userId.id)
            .associateBy(
                { ReferenceId(it.referenceId) },
                { it.toModel() }
            )

    fun fetch(user: User): List<AccountReference> = accountClient.fetchAccounts(user)

    fun register(userId: UserId, accounts: List<AccountReference>): List<Account> {
        val entities = accounts.map { AccountEntity.from(userId = userId, account = it) }.toList()
        val saved = accountRepository.saveAll(entities)
        return saved.map { it.toModel() }
    }
}