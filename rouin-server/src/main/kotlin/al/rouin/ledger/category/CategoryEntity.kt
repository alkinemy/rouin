package al.rouin.ledger.category

import al.rouin.common.AuditEntity
import al.rouin.common.Constants.DEFAULT_CATEGORY_NAME
import al.rouin.user.UserId
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "categories")
class CategoryEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(name = "category_id", unique = true)
    val categoryId: String,
    @Column(name = "user_id")
    val userId: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "budget")
    val budget: Double? = null,
) : AuditEntity() {

    companion object {
        fun from(userId: UserId, name: String, budget: Double) = CategoryEntity(
            categoryId = CategoryId.newId().id,
            userId = userId.id,
            name = name,
            budget = budget
        )

        fun defaultCategory(userId: UserId) = CategoryEntity(
            categoryId = CategoryId.defaultCategoryId(userId).id,
            userId = userId.id,
            name = DEFAULT_CATEGORY_NAME,
        )
    }

    @Transient
    fun toModel() = Category(
        categoryId = CategoryId(categoryId),
        userId = UserId(userId),
        name = name,
        budget = budget
    )
}
