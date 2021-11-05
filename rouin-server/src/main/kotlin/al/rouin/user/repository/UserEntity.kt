package al.rouin.user.repository

import al.rouin.common.AuditEntity
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
) : AuditEntity()