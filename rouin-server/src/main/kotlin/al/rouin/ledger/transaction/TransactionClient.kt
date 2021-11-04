package al.rouin.ledger.transaction

import al.rouin.common.AccountId
import al.rouin.common.Constants.EMPTY_STRING
import al.rouin.config.plaid.executeBody
import al.rouin.currency.CurrencyCode
import al.rouin.ledger.Transaction
import al.rouin.token.accesstoken.AccessToken
import com.plaid.client.model.TransactionsGetRequest
import com.plaid.client.model.TransactionsGetRequestOptions
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TransactionClient(
    private val plaidApi: PlaidApi,
) {
    fun fetchTransactions(transactionForm: TransactionForm): List<Transaction> =
        transactionForm.user.accessTokens
            .flatMap {
                fetchTransactions(
                    accessToken = it,
                    from = transactionForm.from,
                    to = transactionForm.to,
                )
            }.toList()

    private fun fetchTransactions(accessToken: AccessToken, from: LocalDate, to: LocalDate): List<Transaction> {
        val request = TransactionsGetRequest()
            .accessToken(accessToken.token)
            .startDate(from)
            .endDate(to)
            .options(TransactionsGetRequestOptions().count(100)) //TODO paging
        val response = plaidApi.transactionsGet(request).executeBody()
        return response.transactions.map {
            Transaction(
                transactionId = it.transactionId,
                transactionName = it.name,
                accountId = AccountId.id(it.accountId),
                amount = it.amount,
                currencyCode = CurrencyCode.valueOf(it.isoCurrencyCode ?: it.unofficialCurrencyCode!!),
                description = EMPTY_STRING
            )
        }
    }
}