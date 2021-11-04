package al.rouin.ledger

import al.rouin.common.AccountId
import al.rouin.common.TransactionId
import al.rouin.common.UserId
import al.rouin.currency.CurrencyCode
import al.rouin.ledger.account.AccountSubType
import al.rouin.ledger.account.AccountType
import java.time.LocalDate


data class Account(
    val accountId: AccountId,
    val name: String,
    val aliasName: String,
    val officialName: String?,
    val accountType: AccountType,
    val accountSubType: AccountSubType?,
)


data class Transaction(
    val transactionId: TransactionId,
    val userId: UserId,
    val accountId: AccountId,
    val name: String,
    val amount: Double,
    val date: LocalDate,
    val currency: CurrencyCode,
    val description: String,
)