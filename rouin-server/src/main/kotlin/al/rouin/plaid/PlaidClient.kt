package al.rouin.plaid

import al.rouin.config.plaid.PlaidProperties
import al.rouin.token.accesstoken.AccessToken
import al.rouin.token.temporarytoken.LinkToken
import al.rouin.token.temporarytoken.PublicToken
import com.plaid.client.model.ItemPublicTokenExchangeRequest
import com.plaid.client.model.LinkTokenCreateRequest
import com.plaid.client.model.LinkTokenCreateRequestUser
import com.plaid.client.model.Products
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service
import retrofit2.Call

@Service
class PlaidClient(
    private val plaidApi: PlaidApi,
    private val plaidProperties: PlaidProperties,
) {
    fun issueToken(id: String): LinkToken {
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

    fun exchangePublicToken(token: PublicToken): AccessToken {
        val request = ItemPublicTokenExchangeRequest()
            .publicToken(token.token)
        val response = plaidApi.itemPublicTokenExchange(request).executeBody()
        return AccessToken(
            token = response.accessToken,
            itemId = response.itemId
        )
    }

    fun <T> Call<T>.executeBody(): T {
        val response = this.execute()
        return response.body()!!
    }
}