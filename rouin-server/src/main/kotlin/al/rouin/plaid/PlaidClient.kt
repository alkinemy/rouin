package al.rouin.plaid

import al.rouin.account.Transaction
import al.rouin.account.TransactionForm
import al.rouin.common.AccountId
import al.rouin.config.plaid.PlaidProperties
import al.rouin.currency.CurrencyCode
import al.rouin.token.accesstoken.AccessToken
import al.rouin.token.temporarytoken.LinkToken
import al.rouin.token.temporarytoken.PublicToken
import com.plaid.client.model.*
import com.plaid.client.model.Products.*
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service
import retrofit2.Call
import java.time.LocalDate

@Service
class PlaidClient(
    private val plaidApi: PlaidApi,
    private val plaidProperties: PlaidProperties,
) {
    fun issueToken(id: String): LinkToken {
        val request = LinkTokenCreateRequest()
            .user(LinkTokenCreateRequestUser().clientUserId(id))
            .clientName(id)
            .products(listOf(AUTH, TRANSACTIONS))
            .countryCodes(plaidProperties.countryCode.map { it.countryCode })
            .language(plaidProperties.language)
            .redirectUri(plaidProperties.redirectUri)
        val response = plaidApi.linkTokenCreate(request).executeBody()
        return LinkToken(token = response.linkToken)
    }

    fun exchangePublicToken(token: PublicToken): AccessToken {
        val request = ItemPublicTokenExchangeRequest()
            .publicToken(token.token)
        val response = plaidApi.itemPublicTokenExchange(request).executeBody()
        return AccessToken(
            token = response.accessToken,
            itemId = response.itemId
        )
    }

    fun getTransactions(transactionForm: TransactionForm): List<Transaction> =
        transactionForm.user.accessTokens
            .flatMap {
                getTransactions(
                    accessToken = it,
                    from = transactionForm.from,
                    to = transactionForm.to,
                )
            }.toList()

    private fun getTransactions(accessToken: AccessToken, from: LocalDate, to: LocalDate): List<Transaction> {
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
                currencyCode = CurrencyCode.valueOf(it.isoCurrencyCode ?: it.unofficialCurrencyCode!!)
            ) //TODO mapping
        }
    }

    private fun <T> Call<T>.executeBody(): T {
        val response = this.execute()
        return response.body()!!
    }
}