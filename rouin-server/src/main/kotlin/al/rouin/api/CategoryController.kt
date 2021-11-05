package al.rouin.api

import al.rouin.ledger.category.Category
import al.rouin.ledger.category.CategoryId
import al.rouin.ledger.category.CategoryService
import al.rouin.user.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping("/api/v1/ledgers/{userId}/categories")
    fun getCategories(@PathVariable userId: String): List<CategoryDto> = categoryService.get(UserId(userId))
        .map { CategoryDto.from(it) }
}


data class CategoryDto(
    val categoryId: CategoryId,
    val userId: UserId,
    val name: String,
    val budget: Double,
) {
    companion object {
        fun from(model: Category) = with(model) {
            CategoryDto(
                categoryId = categoryId,
                userId = userId,
                name = name,
                budget = budget ?: 0.0 //TODO set budget
            )
        }
    }
}
