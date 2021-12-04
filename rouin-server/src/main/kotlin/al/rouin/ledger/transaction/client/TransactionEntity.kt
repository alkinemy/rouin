package al.rouin.ledger.transaction.client

import al.rouin.common.AuditEntity
import al.rouin.common.Constants.EMPTY_STRING
import al.rouin.external.ReferenceId
import al.rouin.ledger.account.AccountId
import al.rouin.ledger.category.CategoryId
import al.rouin.ledger.exchange.CurrencyCode
import al.rouin.ledger.transaction.Transaction
import al.rouin.ledger.transaction.TransactionId
import al.rouin.ledger.transaction.TransactionReference
import al.rouin.user.UserId
import java.time.LocalDateTime
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
    val transactionId: TransactionId,
    @Column(name = "reference_id")
    val referenceId: ReferenceId,
    @Column(name = "user_id")
    val userId: UserId,
    @Column(name = "account_id")
    val accountId: AccountId,
    @Column(name = "category_id")
    val categoryId: CategoryId,
    @Column(name = "name")
    val name: String,
    @Column(name = "date")
    val date: LocalDateTime,
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
                transactionId = TransactionId.newId(),
                referenceId = transactionReferenceId,
                userId = userId,
                accountId = accountId,
                categoryId = CategoryId.uncategorized(userId),
                name = name,
                amount = amount,
                date = date.atStartOfDay(),
                currency = currency,
                description = EMPTY_STRING,
                deleted = false,
            )
        }
    }

    @Transient
    fun toModel() = Transaction(
        transactionId = transactionId,
        referenceId = referenceId,
        userId = userId,
        accountId = accountId,
        categoryId = categoryId,
        name = name,
        amount = amount,
        date = date,
        currency = currency,
        description = description,
    )
}
