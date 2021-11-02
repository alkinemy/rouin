package al.rouin.user.repository

import al.rouin.common.RouinRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : RouinRepository<UserEntity, Long> {
    fun findByUserId(userId: String): UserEntity?
}