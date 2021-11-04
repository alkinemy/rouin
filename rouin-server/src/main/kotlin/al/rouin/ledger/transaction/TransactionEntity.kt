package al.rouin.ledger.transaction

import al.rouin.common.AccountId
import al.rouin.common.Constants.EMPTY_STRING
import al.rouin.common.TransactionId
import al.rouin.common.UserId
import al.rouin.currency.CurrencyCode
import al.rouin.ledger.Transaction
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
) {
    companion object {
        fun from(userId: UserId, accountId: AccountId, transaction: TransactionReference) = TransactionEntity(
            transactionId = TransactionId.newId().id,
            referenceId = transaction.transactionReferenceId.id,
            userId = userId.id,
            accountId = accountId.id,
            name = transaction.name,
            amount = transaction.amount,
            date = transaction.date,
            currency = transaction.currency,
            description = EMPTY_STRING,
            deleted = false,
        )
    }

    @Transient
    fun toModel() = Transaction(
        transactionId = TransactionId.id(transactionId),
        userId = UserId.id(userId),
        accountId = AccountId.id(accountId),
        name = name,
        amount = amount,
        date = date,
        currency = currency,
        description = description,
    )
}
