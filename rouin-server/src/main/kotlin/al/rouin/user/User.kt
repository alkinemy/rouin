package al.rouin.user

import al.rouin.token.AccessToken

data class User (
    val userId: String,
    val email: String,
    val accessToken: AccessToken,
)