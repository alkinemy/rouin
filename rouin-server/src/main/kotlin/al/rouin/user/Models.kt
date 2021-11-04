package al.rouin.user

import al.rouin.token.accesstoken.AccessToken
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*


data class User(
    val userId: UserId,
    val email: String,
    val accessTokens: List<AccessToken>,
)


data class UserId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
    val id: String
) {
    companion object {
        fun newId() = UserId(id = UUID.randomUUID().toString())
    }

    @JsonValue
    override fun toString() = id
}