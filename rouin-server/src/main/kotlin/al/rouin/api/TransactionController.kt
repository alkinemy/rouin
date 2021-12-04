package al.rouin.api

import al.rouin.ledger.LedgerService
import al.rouin.ledger.exchange.CurrencyCode
import al.rouin.ledger.transaction.RichTransaction
import al.rouin.ledger.transaction.TransactionId
import al.rouin.user.UserId
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.YearMonth

@RestController
class TransactionController(
    private val ledgerService: LedgerService,
) {
    @GetMapping("/api/v1/ledgers/{userId}/transactions")
    fun getTransactions(
        @PathVariable userId: UserId,
        @RequestParam("year") year: Int, //TODO validation
        @RequestParam("month") month: Int,
    ): List<RichTransactionDto> =
        ledgerService.getRichTransactions(userId = userId, yearMonth = YearMonth.of(year, month))
            .map { RichTransactionDto.from(it) }

    @PostMapping("/api/v1/ledgers/{userId}/transactions/sync")
    fun syncTransactions(@PathVariable userId: UserId) =
        ledgerService.syncTransactions(userId)
}


data class RichTransactionDto(
    val transactionId: TransactionId,
    val userId: UserId,
    val account: AccountDto,
    val category: CategoryDto,
    val name: String,
    val amount: Double,
    val date: LocalDateTime,
    val currency: CurrencyCode,
    val description: String,
) {
    companion object {
        fun from(model: RichTransaction) = with(model) {
            RichTransactionDto(
                transactionId = transactionId,
                userId = userId,
                account = AccountDto.from(account),
                category = CategoryDto.from(category),
                name = name,
                amount = amount,
                date = date,
                currency = currency,
                description = description,
            )
        }
    }
}