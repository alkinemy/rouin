package al.rouin.user

import al.rouin.common.UserNotFoundException
import al.rouin.ledger.category.CategoryService
import al.rouin.token.PublicToken
import al.rouin.token.TokenService
import al.rouin.user.repository.UserEntity
import al.rouin.user.repository.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService(
    private val tokenService: TokenService,
    private val categoryService: CategoryService,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun create(email: String): UserId {
        val entity = userRepository.save(
            UserEntity(
                userId = UserId.newId(),
                email = email
            )
        )
        val userId = entity.userId
        categoryService.initializeUserCategories(userId)
        return userId
    }

    fun registerToken(userId: UserId, token: PublicToken) {
        val accessToken = tokenService.exchangePublicTokenToAccessToken(token)
        tokenService.saveAccessToken(userId = userId, accessToken = accessToken)
    }

    fun getUser(userId: UserId): User {
        //TODO join table
        val entity = userRepository.findByUserId(userId) ?: throw UserNotFoundException("User doesn't exist")
        val accessTokens = tokenService.getAccessTokens(userId)
        return User(
            userId = entity.userId,
            email = entity.email,
            accessTokens = accessTokens
        )
    }
}