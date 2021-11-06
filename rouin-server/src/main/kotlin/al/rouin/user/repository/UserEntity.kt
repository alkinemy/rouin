package al.rouin.user.repository

import al.rouin.common.AuditEntity
import al.rouin.user.UserId
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(name = "user_id", unique = true)
    val userId: UserId,
    @Column(name = "email", unique = true)
    val email: String,
) : AuditEntity()