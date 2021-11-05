package al.rouin.plaid

import al.rouin.IntegrationTest
import al.rouin.common.logger
import al.rouin.ledger.account.client.AccountClient
import al.rouin.ledger.transaction.TransactionFetchForm
import al.rouin.ledger.transaction.client.TransactionClient
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
        val accounts = accountClient.fetchAccounts(testUser)
        val transactions = transactionClient.fetch(
            TransactionFetchForm(
                user = testUser,
                from = LocalDate.now().minusDays(30),
                to = LocalDate.now(),
                accountReferenceIds = accounts.map { it.referenceId },
            )
        )
        log.debug("transactions: $transactions")
    }

}