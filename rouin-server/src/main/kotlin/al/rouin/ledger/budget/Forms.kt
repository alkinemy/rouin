package al.rouin.ledger.budget

import al.rouin.ledger.category.CategoryId
import al.rouin.ledger.currency.CurrencyCode
import al.rouin.user.UserId
import java.time.YearMonth


data class BudgetCreationForm(
    val userId: UserId,
    val categoryId: CategoryId,
    val amount: Double,
    val currency: CurrencyCode,
    val yearMonth: YearMonth,
)


data class TotalBudgetCreationForm(
    val userId: UserId,
    val amount: Double,
    val currency: CurrencyCode,
    val yearMonth: YearMonth,
) {
    fun toBudgetCreationForm() = BudgetCreationForm(
        userId = userId,
        categoryId = CategoryId.uncategorized(userId),
        amount = amount,
        currency = currency,
        yearMonth = yearMonth,
    )
}
