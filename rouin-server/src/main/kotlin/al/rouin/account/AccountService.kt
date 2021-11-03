package al.rouin.account

import al.rouin.common.AccountId
import al.rouin.common.UserId
import al.rouin.currency.CurrencyCode
import al.rouin.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AccountService(
    private val userService: UserService,
    private val accountClient: AccountClient,
) {

    fun getTransactions(userId: UserId, from: LocalDate, to: LocalDate): List<Transaction> {
        val user = userService.getUser(userId = userId)
        return accountClient.getTransactions(
            TransactionForm(
                user = user,
                from = from,
                to = to,
            )
        )
    }

}


data class Transaction(
    val transactionId: String,
    val transactionName: String,
    val accountId: AccountId,
    val amount: Double,
    val currencyCode: CurrencyCode,
)
