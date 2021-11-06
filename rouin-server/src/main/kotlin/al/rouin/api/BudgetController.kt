package al.rouin.api

import al.rouin.ledger.budget.CategoryBudget
import al.rouin.ledger.budget.TotalBudget
import al.rouin.ledger.budget.TotalBudgetCreationForm
import al.rouin.ledger.budget.TotalBudgetService
import al.rouin.ledger.category.CategoryId
import al.rouin.ledger.currency.CurrencyCode
import al.rouin.user.UserId
import org.springframework.web.bind.annotation.*
import java.time.YearMonth

@RestController
class BudgetController(
    private val totalBudgetService: TotalBudgetService,
) {
    @GetMapping("/api/v1/ledgers/{userId}/budgets")
    fun getTotalBudget(
        @PathVariable userId: UserId,
        @RequestParam("year") budgetYear: Int,
        @RequestParam("month") budgetMonth: Int
    ): TotalBudgetDto {
        val totalBudget = totalBudgetService.getTotalBudget(
            userId = userId,
            yearMonth = YearMonth.of(budgetYear, budgetMonth)
        )
        return TotalBudgetDto.from(totalBudget)
    }

    @PostMapping("/api/v1/ledgers/{userId}/budgets/create")
    fun createTotalBudget(
        @PathVariable userId: UserId,
        @RequestBody dto: TotalBudgetCreationDto,
    ): TotalBudgetDto = totalBudgetService.createTotalBudget(dto.toForm(userId))
        .let { TotalBudgetDto.from(it) }
}


data class TotalBudgetCreationDto(
    val year: Int,
    val month: Int,
    val amount: Double,
    val currency: CurrencyCode,
) {
    fun toForm(userId: UserId): TotalBudgetCreationForm =
        TotalBudgetCreationForm(
            userId = userId,
            amount = amount,
            currency = currency,
            yearMonth = YearMonth.of(year, month),
        )
}


data class TotalBudgetDto(
    val year: Int,
    val month: Int,
    val currency: CurrencyCode,
    val amount: Double,
    val userId: UserId,
    val categories: List<CategoryBudgetDto>
) {
    companion object {
        fun from(model: TotalBudget) = with(model) {
            TotalBudgetDto(
                year = yearMonth.year,
                month = yearMonth.monthValue,
                currency = currency,
                amount = amount,
                userId = userId,
                categories = categories.map { CategoryBudgetDto.from(it) }
            )
        }
    }
}


data class CategoryBudgetDto(
    val categoryId: CategoryId,
    val categoryName: String,
    val amount: Double? = null,
) {
    companion object {
        fun from(model: CategoryBudget) = with(model) {
            CategoryBudgetDto(
                categoryId = categoryId,
                categoryName = categoryName,
                amount = amount
            )
        }
    }
}