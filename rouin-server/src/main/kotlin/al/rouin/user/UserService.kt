package al.rouin.user

import al.rouin.common.UserId
import al.rouin.token.PublicToken
import al.rouin.token.TokenService
import al.rouin.token.accesstoken.UserNotFoundException
import al.rouin.user.repository.UserEntity
import al.rouin.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
) {
    fun create(email: String): UserId {
        val entity = userRepository.save(
            UserEntity(
                userId = UserId.newId().id,
                email = email
            )
        )
        return UserId(entity.userId)
    }

    fun registerToken(userId: UserId, token: PublicToken) {
        val accessToken = tokenService.exchangePublicTokenToAccessToken(token)
        tokenService.saveAccessToken(userId = userId, accessToken = accessToken)
    }

    fun getUser(userId: UserId): User {
        //TODO join table
        val entity = userRepository.findByUserId(userId = userId.id) ?: throw UserNotFoundException("User doesn't exist")
        val accessTokens = tokenService.getAccessTokens(userId = userId)
        return User(
            userId = UserId.id(entity.userId),
            email = entity.email,
            accessTokens = accessTokens
        )
    }
}