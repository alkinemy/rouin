package al.rouin.ledger.transaction

import al.rouin.ledger.currency.CurrencyCode
import al.rouin.external.ReferenceId
import al.rouin.ledger.account.AccountId
import al.rouin.user.UserId
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.time.LocalDate
import java.util.*


data class Transaction(
    val transactionId: TransactionId,
    val referenceId: ReferenceId,
    val userId: UserId,
    val accountId: AccountId,
    val name: String,
    val amount: Double,
    val date: LocalDate,
    val currency: CurrencyCode,
    val description: String,
)


data class TransactionId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
    val id: String
) {

    companion object {
        fun newId() = TransactionId(id = UUID.randomUUID().toString())
    }

    @JsonValue
    override fun toString() = id
}