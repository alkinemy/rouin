package al.rouin.account

import al.rouin.account.transaction.TransactionForm
import al.rouin.common.UserId
import al.rouin.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AccountService(
    private val userService: UserService,
    private val accountClient: AccountClient,
) {
    fun getAccounts(userId: UserId): List<Account> {
        val user = userService.getUser(userId = userId)
        return accountClient.fetchAccounts(user)
    }

    fun getTransactions(userId: UserId, from: LocalDate, to: LocalDate): List<Transaction> {
        val user = userService.getUser(userId = userId)
        return accountClient.fetchTransactions(
            TransactionForm(
                user = user,
                from = from,
                to = to,
            )
        )
    }
}

