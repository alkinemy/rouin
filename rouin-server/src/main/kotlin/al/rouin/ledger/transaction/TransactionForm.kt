package al.rouin.ledger.transaction

import al.rouin.external.ReferenceId
import al.rouin.user.User
import java.time.LocalDate


data class TransactionForm(
    val user: User,
    val from: LocalDate,
    val to: LocalDate,
    val accountReferenceIds: List<ReferenceId>
)