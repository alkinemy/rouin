package al.rouin.ledger.transaction

import al.rouin.external.ReferenceId
import al.rouin.ledger.account.AccountId
import al.rouin.ledger.category.CategoryId
import al.rouin.ledger.currency.CurrencyCode
import al.rouin.user.UserId
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING
import com.fasterxml.jackson.annotation.JsonValue
import java.time.LocalDate
import java.util.*


data class Transaction(
    val transactionId: TransactionId,
    val referenceId: ReferenceId,
    val userId: UserId,
    val accountId: AccountId,
    val categoryId: CategoryId,
    val name: String,
    val amount: Double,
    val date: LocalDate,
    val currency: CurrencyCode,
    val description: String,
)


data class TransactionId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {

    companion object {
        fun newId() = TransactionId(id = UUID.randomUUID().toString())
    }

    @JsonValue
    override fun toString() = id
}

data class TransactionReference(
    val transactionReferenceId: ReferenceId,
    val accountReferenceId: ReferenceId,
    val name: String,
    val amount: Double,
    val date: LocalDate,
    val currency: CurrencyCode,
)