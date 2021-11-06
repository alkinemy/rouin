package al.rouin.user.repository

import al.rouin.common.RouinRepository
import al.rouin.user.UserId
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : RouinRepository<UserEntity, Long> {
    fun findByUserId(userId: UserId): UserEntity?
}