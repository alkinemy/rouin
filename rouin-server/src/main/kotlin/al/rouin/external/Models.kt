package al.rouin.external

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING
import com.fasterxml.jackson.annotation.JsonValue


data class ReferenceId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {
    @JsonValue
    override fun toString() = id
}