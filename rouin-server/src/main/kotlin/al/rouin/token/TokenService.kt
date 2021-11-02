package al.rouin.token

import al.rouin.common.UserId
import al.rouin.token.accesstoken.AccessToken
import al.rouin.token.accesstoken.AccessTokenService
import al.rouin.token.temporarytoken.PublicToken
import al.rouin.token.temporarytoken.TokenClient
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenClient: TokenClient,
    private val accessTokenService: AccessTokenService,
) {
    fun issueLinkToken(id: String) = tokenClient.issueLinkToken(id = id)

    fun exchangePublicTokenToAccessToken(token: PublicToken) = tokenClient.exchangePublicTokenToAccessToken(token = token)

    fun saveAccessToken(userId: UserId, accessToken: AccessToken) = accessTokenService.saveAccessToken(userId = userId, accessToken = accessToken)

    fun getAccessTokens(userId: UserId): List<AccessToken> = accessTokenService.getAccessTokens(userId = userId)
}