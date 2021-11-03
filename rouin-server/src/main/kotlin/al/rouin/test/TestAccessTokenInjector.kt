package al.rouin.test

import al.rouin.common.UserId
import al.rouin.common.getLogger
import al.rouin.token.TokenService
import al.rouin.token.accesstoken.AccessToken
import al.rouin.user.repository.UserEntity
import al.rouin.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("local")
@Service
class TestAccessTokenInjector(
    @Value("\${rouin.local-test.auth.email}") private val email: String,
    @Value("\${rouin.local-test.auth.user-id}") private val userId: String,
    @Value("\${rouin.local-test.auth.access-token}") private val accessToken: String,
    @Value("\${rouin.local-test.auth.item-id}") private val itemId: String,
    private val userRepository: UserRepository,
    private val tokenService: TokenService,
) : ApplicationRunner {

    private val log = this.getLogger()

    override fun run(args: ApplicationArguments?) {
        val userEntity = UserEntity(
            email = email,
            userId = userId,
        )
        userRepository.save(userEntity)
        tokenService.saveAccessToken(
            userId = UserId.id(userId),
            accessToken = AccessToken(
                token = accessToken,
                itemId = itemId
            )
        )
        log.debug("[TEST] email: $email")
        log.debug("[TEST] userId: $userId")
        log.debug("[TEST] access token: $accessToken")
        log.debug("[TEST] item id: $itemId")
    }

}