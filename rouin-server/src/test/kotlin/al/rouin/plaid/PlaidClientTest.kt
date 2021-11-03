package al.rouin.plaid

import al.rouin.IntegrationTest
import al.rouin.account.TransactionForm
import al.rouin.common.logger
import al.rouin.user.User
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import java.time.LocalDate

@IntegrationTest
class PlaidClientTest(
    @Autowired private val plaidClient: PlaidClient,
    @Qualifier("testUser") @Autowired private val testUser: User,
) {

    private val log = logger()

    @Test
    fun test() {
        val transactions = plaidClient.getTransactions(
            TransactionForm(
                user = testUser,
                from = LocalDate.now().minusDays(30),
                to = LocalDate.now()
            )
        )
        log.debug("transactions: $transactions")
    }
}