package al.rouin.api

import al.rouin.ledger.LedgerService
import al.rouin.ledger.account.AccountId
import al.rouin.ledger.currency.CurrencyCode
import al.rouin.ledger.transaction.Transaction
import al.rouin.ledger.transaction.TransactionId
import al.rouin.user.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class TransactionController(
    private val ledgerService: LedgerService,
) {
    @GetMapping("/api/v1/ledgers/{userId}/transactions")
    fun getTransactions(@PathVariable userId: UserId): List<TransactionDto> =
        ledgerService.getTransactions(userId)
            .map { TransactionDto.from(it) }

    @PostMapping("/api/v1/ledgers/{userId}/transactions/sync")
    fun syncTransactions(@PathVariable userId: UserId) =
        ledgerService.syncTransactions(userId)
}


data class TransactionDto(
    val transactionId: TransactionId,
    val userId: UserId,
    val accountId: AccountId,
    val name: String,
    val amount: Double,
    val date: LocalDate,
    val currency: CurrencyCode,
    val description: String,
) {
    companion object {
        fun from(model: Transaction) = TransactionDto(
            transactionId = model.transactionId,
            userId = model.userId,
            accountId = model.accountId,
            name = model.name,
            amount = model.amount,
            date = model.date,
            currency = model.currency,
            description = model.description,
        )
    }
}