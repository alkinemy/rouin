package al.rouin.api

import al.rouin.ledger.LedgerService
import al.rouin.ledger.account.Account
import al.rouin.ledger.account.AccountId
import al.rouin.ledger.account.AccountSubType
import al.rouin.ledger.account.AccountType
import al.rouin.user.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val ledgerService: LedgerService,
) {
    @GetMapping("/api/v1/ledgers/{userId}/accounts")
    fun getAccounts(@PathVariable userId: String): List<AccountDto> =
        ledgerService.getAccounts(userId.id())
            .map { AccountDto.from(it) }

    @PostMapping("/api/v1/ledgers/{userId}/accounts/sync")
    fun syncAccounts(@PathVariable userId: String) =
        ledgerService.syncAccounts(userId.id())

    private fun String.id() = UserId(this)
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
            alias = model.aliasName,
            officialName = model.officialName,
            type = model.accountType,
            subType = model.accountSubType,
        )
    }
}