package al.rouin.common

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING
import java.util.*

data class UserId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {
    companion object {
        fun newId() = UserId(id = UUID.randomUUID().toString())
        fun id(id: String) = UserId(id = id)
    }

    override fun toString() = id
}


data class AccountId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {
    companion object {
        fun id(id: String) = AccountId(id = id)
    }

    override fun toString() = id
}