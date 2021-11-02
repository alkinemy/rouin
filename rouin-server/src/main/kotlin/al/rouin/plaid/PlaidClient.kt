package al.rouin.plaid

import al.rouin.config.plaid.PlaidProperties
import al.rouin.token.AccessItemToken
import al.rouin.token.LinkToken
import al.rouin.token.PublicToken
import com.plaid.client.model.*
import com.plaid.client.request.PlaidApi
import org.springframework.stereotype.Service
import retrofit2.Response

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
        val response = plaidApi.linkTokenCreate(request).execute()
        return LinkToken(token = response.body()!!.linkToken)
    }

    fun exchangePublicToken(token: PublicToken): AccessItemToken {
        TODO()
    }
}