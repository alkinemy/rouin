package al.rouin.user

import al.rouin.config.RouinRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : RouinRepository<UserEntity, Long>