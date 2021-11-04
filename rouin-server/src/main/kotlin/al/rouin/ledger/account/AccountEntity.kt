package al.rouin.ledger.account

import al.rouin.common.AccountId
import al.rouin.common.UserId
import al.rouin.ledger.Account
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "accounts")
class AccountEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(name = "account_id", unique = true)
    val accountId: String,
    @Column(name = "plaid_account_id", unique = true)
    val plaidAccountId: String,
    @Column(name = "user_id")
    val userId: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "alias_name")
    val aliasName: String,
    @Column(name = "official_name")
    val officialName: String?,
    @Column(name = "account_type")
    val accountType: String,
    @Column(name = "account_sub_type")
    val accountSubType: String?
) {
    companion object {
        fun from(userId: UserId, account: AccountReference): AccountEntity = AccountEntity(
            accountId = AccountId.newId().id,
            plaidAccountId = account.id.id,
            userId = userId.id,
            name = account.name,
            aliasName = account.aliasName,
            officialName = account.officialName,
            accountType = account.accountType.name,
            accountSubType = account.accountSubType?.name,
        )
    }

    @Transient
    fun toModel() = Account(
        accountId = AccountId.id(accountId),
        name = name,
        aliasName = aliasName,
        officialName = officialName,
        accountType = AccountType.toAccountType(accountType),
        accountSubType = accountSubType?.let { AccountSubType.toAccountSubType(it) }
    )
}
