package al.rouin.config.test

import al.rouin.common.logger
import al.rouin.ledger.category.CategoryService
import al.rouin.token.TokenService
import al.rouin.token.accesstoken.AccessToken
import al.rouin.user.User
import al.rouin.user.UserId
import al.rouin.user.repository.UserEntity
import al.rouin.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("local", "test")
@Configuration
class TestConfig(
    @Value("\${rouin.local-test.auth.email}") private val email: String,
    @Value("\${rouin.local-test.auth.user-id}") private val userId: String,
    @Value("\${rouin.local-test.auth.access-token}") private val accessToken: String,
    @Value("\${rouin.local-test.auth.item-id}") private val itemId: String,
) {
    @Bean
    fun testUser(): User {
        return User(
            userId = UserId(userId),
            email = email,
            accessTokens = listOf(AccessToken(token = accessToken, itemId = itemId))
        )
    }
}


@Profile("local", "test")
@Service
class TestAccessTokenInjector(
    @Qualifier("testUser") private val testUser: User,
    private val userRepository: UserRepository,
    private val categoryService: CategoryService,
    private val tokenService: TokenService,
) : ApplicationRunner {

    private val log = this.logger()

    override fun run(args: ApplicationArguments?) {
        val userEntity = UserEntity(
            email = testUser.email,
            userId = testUser.userId,
        )
        userRepository.save(userEntity)
        categoryService.initializeUserCategories(testUser.userId)
        log.debug("[TEST] email: ${testUser.email}")
        log.debug("[TEST] userId: ${testUser.userId}")
        testUser.accessTokens.forEach {
            tokenService.saveAccessToken(
                userId = testUser.userId,
                accessToken = AccessToken(
                    token = it.token,
                    itemId = it.itemId
                )
            )
            log.debug("[TEST] access token: $it")
        }
    }

}