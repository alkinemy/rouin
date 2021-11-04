package al.rouin.ledger

import al.rouin.common.UserId
import al.rouin.ledger.account.AccountClient
import al.rouin.ledger.transaction.TransactionClient
import al.rouin.ledger.transaction.TransactionForm
import al.rouin.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LedgerService(
    private val userService: UserService,
    private val accountClient: AccountClient,
    private val transactionClient: TransactionClient,
) {
    fun getAccounts(userId: UserId): List<Account> {
        val user = userService.getUser(userId = userId)
        return accountClient.fetchAccounts(user)
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

