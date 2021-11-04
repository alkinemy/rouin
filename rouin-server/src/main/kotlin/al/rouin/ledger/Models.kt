package al.rouin.ledger

import al.rouin.ledger.account.AccountSubType
import al.rouin.ledger.account.AccountType
import al.rouin.common.AccountId
import al.rouin.currency.CurrencyCode


data class Account(
    val accountId: AccountId,
    val name: String,
    val aliasName: String,
    val officialName: String?,
    val accountType: AccountType,
    val accountSubType: AccountSubType?,
)


data class Transaction(
    val transactionId: String,
    val transactionName: String,
    val accountId: AccountId,
    val amount: Double,
    val currencyCode: CurrencyCode,
    val description: String,
)