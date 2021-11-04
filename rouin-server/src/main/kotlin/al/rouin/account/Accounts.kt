package al.rouin.account

import al.rouin.common.AccountId
import al.rouin.currency.CurrencyCode


data class Account(
    val accountId: AccountId,
    val name: String,
    val alias: String,
    val officialName: String?,
    val type: AccountType,
    val subType: AccountSubType?,
)


data class Transaction(
    val transactionId: String,
    val transactionName: String,
    val accountId: AccountId,
    val amount: Double,
    val currencyCode: CurrencyCode,
    val description: String,
)