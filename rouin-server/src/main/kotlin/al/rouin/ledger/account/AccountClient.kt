package al.rouin.ledger.account

import al.rouin.common.Constants.EMPTY_STRING
import al.rouin.common.ReferenceId
import al.rouin.plaid.executeBody
import al.rouin.user.User
import com.plaid.client.model.AuthGetRequest
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service

@Service
class AccountClient(
    private val plaidApi: PlaidApi,
) {
    fun fetchAccounts(user: User): List<AccountReference> =
        user.accessTokens.flatMap {
            val request = AuthGetRequest().accessToken(it.token)
            val response = plaidApi.authGet(request).executeBody()
            response.accounts.map { account ->
                AccountReference(
                    id = ReferenceId.id(account.accountId),
                    name = account.name,
                    aliasName = EMPTY_STRING,
                    officialName = account.officialName,
                    accountType = AccountType.toAccountType(account.type),
                    accountSubType = account.subtype?.let { subType ->
                        AccountSubType.toAccountSubType(subType)
                    }
                )
            }
        }.toList()
}


data class AccountReference(
    val id: ReferenceId,
    val name: String,
    val aliasName: String,
    val officialName: String?,
    val accountType: AccountType,
    val accountSubType: AccountSubType?,
)