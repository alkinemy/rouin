package al.rouin.ledger.category

import al.rouin.user.UserId
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun getCategories(userId: UserId): List<Category> = categoryRepository.findByUserId(userId)
        .filterNot { it.categoryId == CategoryId.uncategorized(userId) }
        .map { it.toModel() }

    fun initializeUserCategories(userId: UserId) {
        val entity = CategoryEntity.uncategorized(userId = userId)
        categoryRepository.save(entity)
    }
}