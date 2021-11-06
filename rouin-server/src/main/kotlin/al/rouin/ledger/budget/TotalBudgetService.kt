package al.rouin.ledger.budget

import al.rouin.common.BudgetNotFoundException
import al.rouin.ledger.category.CategoryService
import al.rouin.user.UserId
import org.springframework.stereotype.Service
import java.time.YearMonth

@Service
class TotalBudgetService(
    private val budgetService: BudgetService,
    private val categoryService: CategoryService
) {
    fun createTotalBudget(totalBudgetCreationForm: TotalBudgetCreationForm): TotalBudget {
        val budgetCreationForm = totalBudgetCreationForm.toBudgetCreationForm()
        budgetService.createBudget(budgetCreationForm)
        return getTotalBudget(
            userId = totalBudgetCreationForm.userId,
            yearMonth = totalBudgetCreationForm.yearMonth,
        )
    }

    fun getTotalBudget(userId: UserId, yearMonth: YearMonth): TotalBudget {
        val budgets = budgetService.getBudgets(userId = userId, yearMonth = yearMonth)
        if (budgets.isEmpty()) {
            throw BudgetNotFoundException("User $userId doesn't have the budget on $yearMonth")
        }
        val categoryIdToBudget = budgets.associateBy { it.categoryId }
        val categories = categoryService.getCategories(userId)
        return TotalBudget(
            yearMonth = yearMonth,
            currency = budgets.first().currency,
            amount = budgets.sumOf { it.amount },
            userId = userId,
            categories = categories
                .map { category ->
                    categoryIdToBudget[category.categoryId]
                        ?.let { budget -> CategoryBudget.withBudget(category = category, budget = budget) }
                        ?: let { CategoryBudget.noBudget(category) }
                }
        )
    }
}