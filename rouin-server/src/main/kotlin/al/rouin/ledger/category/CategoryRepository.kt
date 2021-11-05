package al.rouin.ledger.category

import al.rouin.common.RouinRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : RouinRepository<CategoryEntity, Long> {
    fun findByUserId(userId: String): List<CategoryEntity>
}
