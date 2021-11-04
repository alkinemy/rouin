package al.rouin.ledger.account

import al.rouin.common.ReferenceId
import al.rouin.common.UserId
import al.rouin.ledger.Account
import al.rouin.user.User
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val accountClient: AccountClient,
) {
    fun getAccounts(userId: UserId): List<Account> = accountRepository.findByUserId(userId.id)
        .map { it.toModel() }

    fun getByReferenceId(userId: UserId): Map<ReferenceId, Account> =
        accountRepository.findByUserId(userId.id)
            .associateBy({
                ReferenceId.id(it.referenceId)
            }, {
                it.toModel()
            })

    fun fetch(user: User): List<AccountReference> = accountClient.fetchAccounts(user)

    fun register(userId: UserId, accounts: List<AccountReference>): List<Account> {
        val entities = accounts.map { AccountEntity.from(userId = userId, account = it) }.toList()
        val saved = accountRepository.saveAll(entities)
        return saved.map { it.toModel() }
    }
}