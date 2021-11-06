package al.rouin.token.accesstoken

import al.rouin.common.RouinRepository
import al.rouin.user.UserId
import org.springframework.stereotype.Repository

@Repository
interface AccessTokenRepository : RouinRepository<AccessTokenEntity, Long> {
    fun findByUserId(userId: UserId): List<AccessTokenEntity>
}