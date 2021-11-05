package al.rouin.ledger.category

import al.rouin.user.UserId
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

data class Category(
    val categoryId: CategoryId,
    val userId: UserId,
    val name: String,
    val budget: Double?,
)


data class CategoryId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
    val id: String
) {
    companion object {
        fun newId() = CategoryId(id = UUID.randomUUID().toString())
        fun defaultCategoryId(userId: UserId) =
            CategoryId(id = "${userId}_DEFAULT")
    }

    @JsonValue
    override fun toString() = id
}