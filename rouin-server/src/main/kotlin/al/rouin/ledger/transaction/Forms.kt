package al.rouin.ledger.transaction

import al.rouin.external.ReferenceId
import al.rouin.user.User
import al.rouin.user.UserId
import java.time.LocalDate


data class TransactionFetchForm(
    val user: User,
    val from: LocalDate,
    val to: LocalDate,
    val accountReferenceIds: List<ReferenceId>
)


data class TransactionQueryForm(
    val userId: UserId,
    val from: LocalDate,
    val to: LocalDate,
)