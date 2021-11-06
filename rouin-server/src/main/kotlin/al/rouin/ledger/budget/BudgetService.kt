package al.rouin.ledger.budget

import al.rouin.user.UserId
import org.springframework.stereotype.Service
import java.time.YearMonth

@Service
class BudgetService(
    private val budgetRepository: BudgetRepository,
) {
    fun createBudget(form: BudgetCreationForm) {
        val entity = BudgetEntity.from(form)
        budgetRepository.save(entity)
    }

    fun getBudgets(userId: UserId, yearMonth: YearMonth): List<Budget> = budgetRepository.findByUserIdAndBudgetYearAndBudgetMonth(
        userId = userId,
        budgetYear = yearMonth.year,
        budgetMonth = yearMonth.monthValue,
    ).map { it.toModel() }
}