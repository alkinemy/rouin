package al.rouin.ledger.budget

import al.rouin.common.RouinRepository
import al.rouin.user.UserId
import org.springframework.stereotype.Repository

@Repository
interface BudgetRepository : RouinRepository<BudgetEntity, Long> {
    fun findByUserIdAndBudgetYearAndBudgetMonth(userId: UserId, budgetYear: Int, budgetMonth: Int): List<BudgetEntity>
}