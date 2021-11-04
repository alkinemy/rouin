package al.rouin.common

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

data class UserId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {
    companion object {
        fun id(id: String) = UserId(id = id)
        fun newId() = UserId(id = UUID.randomUUID().toString())
    }

    @JsonValue
    override fun toString() = id
}


data class AccountId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {
    companion object {
        fun id(id: String) = AccountId(id = id)
        fun newId() = AccountId(id = UUID.randomUUID().toString())
    }

    @JsonValue
    override fun toString() = id
}

data class TransactionId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {
    companion object {
        fun id(id: String) = TransactionId(id = id)
        fun newId() = TransactionId(id = UUID.randomUUID().toString())
    }

    @JsonValue
    override fun toString() = id
}


data class ReferenceId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {

    companion object {
        fun id(id: String) = ReferenceId(id = id)
    }

    @JsonValue
    override fun toString() = id
}