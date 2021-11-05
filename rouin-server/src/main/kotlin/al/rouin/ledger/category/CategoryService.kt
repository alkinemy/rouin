package al.rouin.ledger.category

import al.rouin.user.UserId
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun get(userId: UserId): List<Category> = categoryRepository.findByUserId(userId.id)
        .map { it.toModel() }

    fun initializeUserCategories(userId: UserId) {
        val entity = CategoryEntity.defaultCategory(userId = userId)
        categoryRepository.save(entity)
    }
}