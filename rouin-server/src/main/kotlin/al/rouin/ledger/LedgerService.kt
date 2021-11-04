package al.rouin.ledger

import al.rouin.common.UserId
import al.rouin.ledger.account.AccountService
import al.rouin.ledger.account.AccountSubType
import al.rouin.ledger.account.AccountType
import al.rouin.ledger.transaction.TransactionClient
import al.rouin.ledger.transaction.TransactionForm
import al.rouin.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LedgerService(
    private val userService: UserService,
    private val accountService: AccountService,
    private val transactionClient: TransactionClient,
) {
    fun getAccounts(userId: UserId): List<Account> = accountService.getAccounts(userId = userId)

    fun syncAccounts(userId: UserId): List<Account> {
        val user = userService.getUser(userId = userId)
        val referenceIdToAccount = accountService.getByReferenceId(userId = userId)
        val fetchedAccount = accountService.fetch(user = user)
        val notRegisteredAccounts = fetchedAccount
            .filterNot { referenceIdToAccount.containsKey(it.id) }
            .filterNot { it.accountType == AccountType.UNSUPPORTED }
            .filterNot { it.accountSubType == AccountSubType.UNSUPPORTED }
            .toList()
        val registeredAccounts = accountService.register(userId, notRegisteredAccounts)
        return referenceIdToAccount.values + registeredAccounts
    }

    fun getTransactions(userId: UserId, from: LocalDate, to: LocalDate): List<Transaction> {
        val user = userService.getUser(userId = userId)
        return transactionClient.fetchTransactions(
            TransactionForm(
                user = user,
                from = from,
                to = to,
            )
        )
    }
}

