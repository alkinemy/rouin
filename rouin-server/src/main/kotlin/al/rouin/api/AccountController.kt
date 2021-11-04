package al.rouin.api

import al.rouin.account.*
import al.rouin.common.AccountId
import al.rouin.common.UserId
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

    @GetMapping("/api/v1/{userId}/accounts")
    fun getAccounts(@PathVariable userId: String): List<AccountDto> =
        accountService.getAccounts(
            userId = UserId.id(userId)
        ).map { AccountDto.from(it) }

    @GetMapping("/api/v1/{userId}/accounts/transactions")
    fun getTransactions(
        @PathVariable userId: String,
        @RequestParam("from") @DateTimeFormat(iso = DATE) from: LocalDate,
        @RequestParam("to") @DateTimeFormat(iso = DATE) to: LocalDate,
    ): List<TransactionDto> =
        accountService.getTransactions(
            userId = UserId.id(userId),
            from = from,
            to = to,
        ).map { TransactionDto.from(it) }
}


data class AccountDto(
    val accountId: AccountId,
    val name: String,
    val alias: String,
    val officialName: String?,
    val type: AccountType,
    val subType: AccountSubType?,
) {
    companion object {
        fun from(model: Account) = AccountDto(
            accountId = model.accountId,
            name = model.name,
            alias = model.alias,
            officialName = model.officialName,
            type = model.type,
            subType = model.subType,
        )
    }
}


data class TransactionDto(
    val transactionId: String, //TODO implementation
    val transactionName: String,
) {
    companion object {
        fun from(model: Transaction) = TransactionDto(
            transactionId = model.transactionId,
            transactionName = model.transactionName,
        )
    }
}