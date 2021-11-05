package al.rouin.ledger.account.client

import al.rouin.common.AuditEntity
import al.rouin.external.ReferenceId
import al.rouin.ledger.account.*
import al.rouin.user.UserId
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "accounts")
class AccountEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(name = "account_id", unique = true)
    val accountId: String,
    @Column(name = "reference_id", unique = true)
    val referenceId: String,
    @Column(name = "user_id")
    val userId: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "alias_name")
    val aliasName: String,
    @Column(name = "official_name")
    val officialName: String?,
    @Column(name = "account_type")
    @Enumerated(STRING)
    val accountType: AccountType,
    @Column(name = "account_sub_type")
    @Enumerated(STRING)
    val accountSubType: AccountSubType?,
    @Column(name = "deleted")
    val deleted: Boolean,
) : AuditEntity() {
    companion object {
        fun from(userId: UserId, account: AccountReference): AccountEntity = with(account) {
            AccountEntity(
                accountId = AccountId.newId().id,
                referenceId = referenceId.id,
                userId = userId.id,
                name = name,
                aliasName = name,
                officialName = officialName,
                accountType = accountType,
                accountSubType = accountSubType,
                deleted = false,
            )
        }
    }

    @Transient
    fun toModel() = Account(
        accountId = AccountId(accountId),
        referenceId = ReferenceId(referenceId),
        name = name,
        aliasName = aliasName,
        officialName = officialName,
        accountType = accountType,
        accountSubType = accountSubType,
    )
}
