package al.rouin.ledger.budget

import al.rouin.ledger.category.Category
import al.rouin.ledger.category.CategoryId
import al.rouin.ledger.exchange.CurrencyCode
import al.rouin.user.UserId
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING
import java.time.YearMonth
import java.util.*


data class TotalBudget(
    val yearMonth: YearMonth,
    val currency: CurrencyCode,
    val amount: Double,
    val userId: UserId,
    val categories: List<CategoryBudget>
)


data class CategoryBudget(
    val categoryId: CategoryId,
    val categoryName: String,
    val amount: Double? = null,
) {
    companion object {
        fun withBudget(budget: Budget, category: Category): CategoryBudget =
            CategoryBudget(
                categoryId = category.categoryId,
                categoryName = category.name,
                amount = budget.amount
            )

        fun noBudget(category: Category): CategoryBudget =
            CategoryBudget(
                categoryId = category.categoryId,
                categoryName = category.name,
            )
    }
}


data class Budget(
    val budgetId: BudgetId,
    val userId: UserId,
    val categoryId: CategoryId,
    val amount: Double,
    val yearMonth: YearMonth,
    val currency: CurrencyCode
)


data class BudgetId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {
    companion object {
        fun newId() = BudgetId(UUID.randomUUID().toString())
    }

    override fun toString() = id
}