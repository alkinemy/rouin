package al.rouin.token

import al.rouin.plaid.PlaidClient
import org.springframework.stereotype.Service

@Service
class TokenClient(
    private val plaidClient: PlaidClient,
) {
    fun issue(id: String): LinkToken = plaidClient.issueToken(id = id)

    fun exchangePublicTokenToAccessToken(token: PublicToken): AccessItemToken = plaidClient.exchangePublicToken(token = token)
}


data class LinkToken(
    val token: String,
)


data class PublicToken(
    val token: String,
)


data class AccessToken(
    val token: String
)


data class AccessItemToken(
    val accessToken: AccessToken,
    val itemId: String,
)