package al.rouin.ledger.budget

import al.rouin.common.AuditEntity
import al.rouin.ledger.category.CategoryId
import al.rouin.ledger.currency.CurrencyCode
import al.rouin.user.UserId
import java.time.YearMonth
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "category_budget")
class BudgetEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(name = "budget_id")
    val budgetId: BudgetId,
    @Column(name = "category_id")
    val categoryId: CategoryId,
    @Column(name = "user_id")
    val userId: UserId,
    @Column(name = "amount")
    val amount: Double,
    @Column(name = "budget_year")
    val budgetYear: Int,
    @Column(name = "budget_month")
    val budgetMonth: Int,
    @Column(name = "currency")
    @Enumerated(STRING)
    val currency: CurrencyCode,
) : AuditEntity() {
    companion object {
        fun from(form: BudgetCreationForm): BudgetEntity = with(form) {
            BudgetEntity(
                budgetId = BudgetId.newId(),
                categoryId = categoryId,
                userId = userId,
                amount = amount,
                budgetYear = yearMonth.year,
                budgetMonth = yearMonth.monthValue,
                currency = currency,
            )
        }
    }

    @Transient
    fun toModel(): Budget =
        Budget(
            budgetId = budgetId,
            categoryId = categoryId,
            userId = userId,
            amount = amount,
            yearMonth = YearMonth.of(budgetYear, budgetMonth),
            currency = currency,
        )
}
