package al.rouin.ledger.account

import al.rouin.common.AccountId
import al.rouin.common.Constants.EMPTY_STRING
import al.rouin.ledger.Account
import al.rouin.plaid.executeBody
import al.rouin.user.User
import com.plaid.client.model.AuthGetRequest
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service

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
}