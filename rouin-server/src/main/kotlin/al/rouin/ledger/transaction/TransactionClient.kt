package al.rouin.ledger.transaction

import al.rouin.common.logger
import al.rouin.currency.CurrencyCode
import al.rouin.external.ReferenceId
import al.rouin.external.executeBody
import al.rouin.token.accesstoken.AccessToken
import al.rouin.user.UserId
import com.plaid.client.model.Transaction
import com.plaid.client.model.TransactionsGetRequest
import com.plaid.client.model.TransactionsGetRequestOptions
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TransactionClient(
    private val plaidApi: PlaidApi,
) {
    private val log = logger()

    companion object {
        private const val FETCH_COUNT = 100
    }

    fun fetch(form: TransactionFetchForm): List<TransactionReference> =
        form.toAccessTokenFetchForm()
            .flatMap { fetch(it) }

    private fun TransactionFetchForm.toAccessTokenFetchForm(): List<TransactionAccessTokenFetchForm> =
        user.accessTokens.map {
            TransactionAccessTokenFetchForm(
                userId = user.userId,
                accessToken = it,
                from = from,
                to = to,
                accountReferenceIds = accountReferenceIds,
            )
        }

    private fun fetch(form: TransactionAccessTokenFetchForm): List<TransactionReference> {
        var offset = 0
        var total: Int
        val transactions: MutableList<TransactionReference> = arrayListOf()
        do {
            val request = buildTransactionGetRequest(form, offset)
            val response = plaidApi.transactionsGet(request).executeBody()
            total = response.totalTransactions
            offset += response.transactions.size
            val fetched = response.transactions.map { it.toReference() }
            log.debug("[TRANSACTION] (${form.userId}) fetched: $offset, total: $total")
            transactions.addAll(fetched)
        } while (offset < total)
        return transactions.toList()
    }

    private fun buildTransactionGetRequest(form: TransactionAccessTokenFetchForm, offset: Int) =
        with(form) {
            TransactionsGetRequest()
                .accessToken(accessToken.token)
                .startDate(from)
                .endDate(to)
                .options(
                    TransactionsGetRequestOptions()
                        .accountIds(accountReferenceIds.map { it.id })
                        .offset(offset)
                        .count(FETCH_COUNT)
                )
        }

    private fun Transaction.toReference() = TransactionReference(
        transactionReferenceId = ReferenceId(transactionId),
        accountReferenceId = ReferenceId(accountId),
        name = name,
        amount = amount,
        date = date,
        currency = CurrencyCode.valueOf(isoCurrencyCode ?: unofficialCurrencyCode!!),
    )
}


data class TransactionReference(
    val transactionReferenceId: ReferenceId,
    val accountReferenceId: ReferenceId,
    val name: String,
    val amount: Double,
    val date: LocalDate,
    val currency: CurrencyCode,
)


data class TransactionAccessTokenFetchForm(
    val userId: UserId,
    val accessToken: AccessToken,
    val from: LocalDate,
    val to: LocalDate,
    val accountReferenceIds: List<ReferenceId>
)