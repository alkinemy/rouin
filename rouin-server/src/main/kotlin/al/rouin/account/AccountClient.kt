package al.rouin.account

import al.rouin.account.transaction.TransactionForm
import al.rouin.common.AccountId
import al.rouin.common.Constants.EMPTY_STRING
import al.rouin.currency.CurrencyCode
import al.rouin.plaid.executeBody
import al.rouin.token.accesstoken.AccessToken
import al.rouin.user.User
import com.plaid.client.model.AuthGetRequest
import com.plaid.client.model.TransactionsGetRequest
import com.plaid.client.model.TransactionsGetRequestOptions
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AccountClient(
    private val plaidApi: PlaidApi,
) {
    fun fetchAccounts(user: User): List<Account> =
        user.accessTokens.flatMap {
            val request = AuthGetRequest().accessToken(it.token)
            val response = plaidApi.authGet(request).executeBody()
            response.accounts.map { account ->
                Account(
                    accountId = AccountId.id(account.accountId),
                    name = account.name,
                    alias = EMPTY_STRING,
                    officialName = account.officialName,
                    type = AccountType.toAccountType(account.type),
                    subType = account.subtype?.let { subType ->
                        AccountSubType.toAccountSubType(subType)
                    }
                )
            }
        }.toList()

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