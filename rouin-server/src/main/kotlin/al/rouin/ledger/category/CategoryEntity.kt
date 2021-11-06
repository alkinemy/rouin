package al.rouin.ledger.category

import al.rouin.common.AuditEntity
import al.rouin.common.Constants.UNCATEGORIZED_CATEGORY_NAME
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
    val categoryId: CategoryId,
    @Column(name = "user_id")
    val userId: UserId,
    @Column(name = "name")
    val name: String,
) : AuditEntity() {

    companion object {
        fun from(userId: UserId, name: String) = CategoryEntity(
            categoryId = CategoryId.newId(),
            userId = userId,
            name = name,
        )

        fun uncategorized(userId: UserId) = CategoryEntity(
            categoryId = CategoryId.uncategorized(userId),
            userId = userId,
            name = UNCATEGORIZED_CATEGORY_NAME,
        )
    }

    @Transient
    fun toModel() = Category(
        categoryId = categoryId,
        userId = userId,
        name = name,
    )
}
