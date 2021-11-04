package al.rouin.ledger.transaction

import al.rouin.common.ReferenceId
import al.rouin.config.plaid.executeBody
import al.rouin.currency.CurrencyCode
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
    fun fetch(transactionForm: TransactionForm): List<TransactionReference> =
        transactionForm.user.accessTokens
            .flatMap {
                fetch(
                    accessToken = it,
                    from = transactionForm.from,
                    to = transactionForm.to,
                )
            }.toList()

    private fun fetch(accessToken: AccessToken, from: LocalDate, to: LocalDate): List<TransactionReference> {
        val request = TransactionsGetRequest()
            .accessToken(accessToken.token)
            .startDate(from)
            .endDate(to)
            .options(TransactionsGetRequestOptions().count(100)) //TODO paging
        val response = plaidApi.transactionsGet(request).executeBody()
        return response.transactions.map {
            TransactionReference(
                transactionReferenceId = ReferenceId.id(it.transactionId),
                accountReferenceId = ReferenceId.id(it.accountId),
                name = it.name,
                amount = it.amount,
                date = it.date,
                currency = CurrencyCode.valueOf(it.isoCurrencyCode ?: it.unofficialCurrencyCode!!),
            )
        }
    }
}


data class TransactionReference(
    val transactionReferenceId: ReferenceId,
    val accountReferenceId: ReferenceId,
    val name: String,
    val amount: Double,
    val date: LocalDate,
    val currency: CurrencyCode,
)