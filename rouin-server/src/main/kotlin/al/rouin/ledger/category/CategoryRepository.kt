package al.rouin.ledger.category

import al.rouin.common.RouinRepository
import al.rouin.user.UserId
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : RouinRepository<CategoryEntity, Long> {
    fun findByUserId(userId: UserId): List<CategoryEntity>
}
