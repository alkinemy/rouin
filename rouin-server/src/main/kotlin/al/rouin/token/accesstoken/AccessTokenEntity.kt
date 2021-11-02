package al.rouin.token.accesstoken

import al.rouin.common.UserId
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "access_tokens")
class AccessTokenEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(name = "user_id", unique = true)
    val userId: String,
    @Column(name = "access_token")
    val accessToken: String,
    @Column(name = "item_id")
    val itemId: String,
) {
    constructor(userId: UserId, accessToken: AccessToken) : this(
        userId = userId.id,
        accessToken = accessToken.token,
        itemId = accessToken.itemId
    )

    @Transient
    fun toAccessToken() = AccessToken(
        token = accessToken,
        itemId = itemId
    )
}