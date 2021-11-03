package al.rouin.account

import al.rouin.plaid.PlaidClient
import org.springframework.stereotype.Service

@Service
class AccountClient(
    private val plaidClient: PlaidClient,
) {
    fun getTransactions(transactionForm: TransactionForm): List<Transaction> = plaidClient.getTransactions(transactionForm)
}