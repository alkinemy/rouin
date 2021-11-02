package al.rouin.user

import al.rouin.common.UserId
import al.rouin.token.accesstoken.AccessToken

data class User(
    val userId: UserId,
    val email: String,
    val accessTokens: List<AccessToken>,
)