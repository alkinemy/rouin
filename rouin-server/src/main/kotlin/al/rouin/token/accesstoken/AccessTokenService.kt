package al.rouin.token.accesstoken

import al.rouin.common.UserId
import org.springframework.stereotype.Service

@Service
class AccessTokenService(
    private val accessTokenRepository: AccessTokenRepository,
) {
    fun getAccessTokens(userId: UserId): List<AccessToken> {
        return accessTokenRepository.findByUserId(userId = userId.id)
            .map { it.toAccessToken() }
    }

    fun saveAccessToken(userId: UserId, accessToken: AccessToken) {
        val entity = AccessTokenEntity(
            userId = userId,
            accessToken = accessToken
        )
        accessTokenRepository.save(entity)
    }
}


data class AccessToken(
    val token: String,
    val itemId: String,
)