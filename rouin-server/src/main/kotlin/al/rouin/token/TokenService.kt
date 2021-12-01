package al.rouin.token

import al.rouin.token.accesstoken.AccessToken
import al.rouin.token.accesstoken.AccessTokenService
import al.rouin.user.User
import al.rouin.user.UserId
import al.rouin.user.UserService
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenClient: TokenClient,
    private val accessTokenService: AccessTokenService,
) {
    fun issueLinkToken(userId: UserId): LinkToken {
        return tokenClient.issueLinkToken(id = userId.id)
    }

    fun exchangePublicTokenToAccessToken(token: PublicToken) = tokenClient.exchangePublicTokenToAccessToken(token = token)

    fun saveAccessToken(userId: UserId, accessToken: AccessToken) = accessTokenService.saveAccessToken(userId = userId, accessToken = accessToken)

    fun getAccessTokens(userId: UserId): List<AccessToken> = accessTokenService.getAccessTokens(userId = userId)
}