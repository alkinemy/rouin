package al.rouin.token.temporarytoken

import al.rouin.plaid.PlaidClient
import al.rouin.token.accesstoken.AccessToken
import org.springframework.stereotype.Service

@Service
class TokenClient(
    private val plaidClient: PlaidClient,
) {
    fun issueLinkToken(id: String): LinkToken = plaidClient.issueToken(id = id)

    fun exchangePublicTokenToAccessToken(token: PublicToken): AccessToken = plaidClient.exchangePublicToken(token = token)
}


data class LinkToken(
    val token: String,
)


data class PublicToken(
    val token: String,
)
