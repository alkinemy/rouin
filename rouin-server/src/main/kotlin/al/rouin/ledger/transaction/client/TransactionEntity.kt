package al.rouin.ledger.transaction.client

import al.rouin.common.AuditEntity
import al.rouin.common.Constants.EMPTY_STRING
import al.rouin.external.ReferenceId
import al.rouin.ledger.account.AccountId
import al.rouin.ledger.currency.CurrencyCode
import al.rouin.ledger.transaction.Transaction
import al.rouin.ledger.transaction.TransactionId
import al.rouin.ledger.transaction.TransactionReference
import al.rouin.user.UserId
import java.time.LocalDate
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "transactions")
class TransactionEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(name = "transaction_id")
    val transactionId: String,
    @Column(name = "reference_id")
    val referenceId: String,
    @Column(name = "user_id")
    val userId: String,
    @Column(name = "account_id")
    val accountId: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "date")
    val date: LocalDate,
    @Column(name = "amount")
    val amount: Double,
    @Column(name = "currency")
    @Enumerated(STRING)
    val currency: CurrencyCode,
    @Column(name = "description")
    val description: String,
    @Column(name = "deleted")
    val deleted: Boolean
) : AuditEntity() {
    companion object {
        fun from(userId: UserId, accountId: AccountId, transaction: TransactionReference) = with(transaction) {
            TransactionEntity(
                transactionId = TransactionId.newId().id,
                referenceId = transactionReferenceId.id,
                userId = userId.id,
                accountId = accountId.id,
                name = name,
                amount = amount,
                date = date,
                currency = currency,
                description = EMPTY_STRING,
                deleted = false,
            )
        }
    }

    @Transient
    fun toModel() = Transaction(
        transactionId = TransactionId(transactionId),
        referenceId = ReferenceId(referenceId),
        userId = UserId(userId),
        accountId = AccountId(accountId),
        name = name,
        amount = amount,
        date = date,
        currency = currency,
        description = description,
    )
}
