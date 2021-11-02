package al.rouin.user

import al.rouin.token.AccessToken
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(name = "user_id", unique = true)
    val userId: String,
    @Column(name = "email", unique = true)
    val email: String,
    @Column(name = "access_token")
    val accessToken: String,
    @Column(name = "item_id")
    val itemId: String,
) {
    @Transient
    fun toUser() = User(
        userId = userId,
        email = email,
        accessToken = AccessToken(token = accessToken)
    )
}