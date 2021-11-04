package al.rouin.plaid

import al.rouin.IntegrationTest
import al.rouin.ledger.account.AccountClient
import al.rouin.ledger.transaction.TransactionForm
import al.rouin.common.logger
import al.rouin.ledger.transaction.TransactionClient
import al.rouin.user.User
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import java.time.LocalDate

@IntegrationTest
class PlaidClientTest(
    @Autowired private val accountClient: AccountClient,
    @Autowired private val transactionClient: TransactionClient,
    @Qualifier("testUser") @Autowired private val testUser: User,
) {

    private val log = logger()

    @Test
    fun fetchAccounts() {
        val accounts = accountClient.fetchAccounts(testUser)
        log.debug("accounts: $accounts")
    }

    @Test
    fun fetchTransactions() {
        val transactions = transactionClient.fetch(
            TransactionForm(
                user = testUser,
                from = LocalDate.now().minusDays(30),
                to = LocalDate.now()
            )
        )
        log.debug("transactions: $transactions")
    }

}