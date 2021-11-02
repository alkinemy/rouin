package al.rouin.token

import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenClient: TokenClient
) {
    fun issue(id: String) = tokenClient.issue(id = id)

    fun exchangePublicTokenToAccessToken(token: PublicToken) = tokenClient.exchangePublicTokenToAccessToken(token = token)
}