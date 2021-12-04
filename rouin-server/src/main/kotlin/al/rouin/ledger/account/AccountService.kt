package al.rouin.ledger.account

import al.rouin.external.ReferenceId
import al.rouin.ledger.account.client.AccountClient
import al.rouin.ledger.account.client.AccountEntity
import al.rouin.ledger.account.client.AccountRepository
import al.rouin.user.User
import al.rouin.user.UserId
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val accountClient: AccountClient,
) {
    fun get(userId: UserId): List<Account> =
        accountRepository.findByUserIdAndDeletedFalse(userId)
            .map { it.toModel() }

    fun getByAccountId(userId: UserId, accountIds: Set<AccountId>): Map<AccountId, Account> =
        accountRepository.findByUserIdAndAccountIdIsInAndDeletedFalse(userId = userId, accountIds = accountIds)
            .associateBy(
                { it.accountId },
                { it.toModel() }
            )

    fun getByReferenceId(userId: UserId): Map<ReferenceId, Account> =
        accountRepository.findByUserIdAndDeletedFalse(userId)
            .associateBy(
                { it.referenceId },
                { it.toModel() }
            )

    fun fetch(user: User): List<AccountReference> = accountClient.fetchAccounts(user)

    fun register(userId: UserId, accounts: List<AccountReference>): List<Account> {
        val entities = accounts.map { AccountEntity.from(userId = userId, account = it) }.toList()
        val saved = accountRepository.saveAll(entities)
        return saved.map { it.toModel() }
    }
}