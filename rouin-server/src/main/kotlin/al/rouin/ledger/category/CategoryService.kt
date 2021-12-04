package al.rouin.ledger.category

import al.rouin.user.UserId
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun getExcludingUnCategorized(userId: UserId): List<Category> =
        categoryRepository.findByUserIdAndCategoryIdNot(userId = userId, categoryId = CategoryId.uncategorized(userId))
            .map { it.toModel() }

    fun getByCategoryId(userId: UserId, categoryIds: Set<CategoryId>): Map<CategoryId, Category> =
        categoryRepository.findByUserIdAndCategoryIdIsIn(userId = userId, categoryIds = categoryIds)
            .associateBy(
                { it.categoryId },
                { it.toModel() }
            )

    fun initializeUserCategories(userId: UserId) {
        val entity = CategoryEntity.uncategorized(userId = userId)
        categoryRepository.save(entity)
    }
}