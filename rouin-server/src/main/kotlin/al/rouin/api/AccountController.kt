package al.rouin.api

import al.rouin.account.AccountService
import al.rouin.account.Transaction
import al.rouin.common.AccountId
import al.rouin.common.UserId
import al.rouin.currency.CurrencyCode
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO.DATE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class AccountController(
    private val accountService: AccountService,
) {
    @GetMapping("/api/v1/{userId}/accounts/transactions")
    fun getTransactions(
        @PathVariable userId: String,
        @RequestParam("from") @DateTimeFormat(iso = DATE) from: LocalDate,
        @RequestParam("to") @DateTimeFormat(iso = DATE) to: LocalDate,
    ): List<TransactionDto> {
        return accountService.getTransactions(
            userId = UserId.id(userId),
            from = from,
            to = to,
        ).map { TransactionDto.from(it) }
    }
}


data class TransactionDto(
    val transactionId: String, //TODO implementation
    val transactionName: String,
) {
    companion object {
        fun from(transaction: Transaction) = TransactionDto(
            transactionId = transaction.transactionId,
            transactionName = transaction.transactionName,
        )
    }
}