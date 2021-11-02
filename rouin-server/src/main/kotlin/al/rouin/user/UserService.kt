package al.rouin.user

import al.rouin.token.PublicToken
import al.rouin.token.TokenService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
) {
    fun create(email: String, publicToken: PublicToken): User {
        val accessItemToken = tokenService.exchangePublicTokenToAccessToken(publicToken)
        val userId = UUID.randomUUID().toString()
        val entity = UserEntity(
            userId = userId,
            email = email,
            accessToken = accessItemToken.accessToken.token,
            itemId = accessItemToken.itemId
        )
        return userRepository.save(entity).toUser()
    }
}