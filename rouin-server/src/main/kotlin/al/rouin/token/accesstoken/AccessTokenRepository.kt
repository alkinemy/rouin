package al.rouin.token.accesstoken

import al.rouin.common.RouinRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessTokenRepository : RouinRepository<AccessTokenEntity, Long> {
    fun findByUserId(userId: String): List<AccessTokenEntity>
}