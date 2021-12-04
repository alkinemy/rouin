package al.rouin.ledger.category

import al.rouin.common.RouinRepository
import al.rouin.user.UserId
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : RouinRepository<CategoryEntity, Long> {
    fun findByUserId(userId: UserId): List<CategoryEntity>
    fun findByUserIdAndCategoryIdNot(userId: UserId, categoryId: CategoryId): List<CategoryEntity>
    fun findByUserIdAndCategoryIdIsIn(userId: UserId, categoryIds: Set<CategoryId>): List<CategoryEntity>
}
