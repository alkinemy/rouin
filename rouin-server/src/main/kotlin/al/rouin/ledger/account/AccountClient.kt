package al.rouin.ledger.account

import al.rouin.common.ReferenceId
import al.rouin.config.plaid.executeBody
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
                    officialName = account.officialName,
                    accountType = AccountType.find(account.type) ?: AccountType.UNSUPPORTED,
                    accountSubType = account.subtype?.let { subType ->
                        AccountSubType.find(subType) ?: AccountSubType.UNSUPPORTED
                    }
                )
            }
        }.toList()
}


data class AccountReference(
    val id: ReferenceId,
    val name: String,
    val officialName: String?,
    val accountType: AccountType,
    val accountSubType: AccountSubType?,
)