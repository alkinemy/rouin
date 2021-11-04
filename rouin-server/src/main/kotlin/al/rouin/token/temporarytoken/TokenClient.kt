package al.rouin.token.temporarytoken

import al.rouin.config.plaid.PlaidProperties
import al.rouin.config.plaid.executeBody
import al.rouin.token.LinkToken
import al.rouin.token.PublicToken
import al.rouin.token.accesstoken.AccessToken
import com.plaid.client.model.ItemPublicTokenExchangeRequest
import com.plaid.client.model.LinkTokenCreateRequest
import com.plaid.client.model.LinkTokenCreateRequestUser
import com.plaid.client.model.Products
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service

@Service
class TokenClient(
    private val plaidApi: PlaidApi,
    private val plaidProperties: PlaidProperties
) {
    fun issueLinkToken(id: String): LinkToken {
        val request = LinkTokenCreateRequest()
            .user(LinkTokenCreateRequestUser().clientUserId(id))
            .clientName(id)
            .products(listOf(Products.AUTH, Products.TRANSACTIONS))
            .countryCodes(plaidProperties.countryCode.map { it.countryCode })
            .language(plaidProperties.language)
            .redirectUri(plaidProperties.redirectUri)
        val response = plaidApi.linkTokenCreate(request).executeBody()
        return LinkToken(token = response.linkToken)
    }

    fun exchangePublicTokenToAccessToken(token: PublicToken): AccessToken {
        val request = ItemPublicTokenExchangeRequest()
            .publicToken(token.token)
        val response = plaidApi.itemPublicTokenExchange(request).executeBody()
        return AccessToken(
            token = response.accessToken,
            itemId = response.itemId
        )
    }
}


