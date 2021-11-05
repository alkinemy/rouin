package al.rouin.ledger

import al.rouin.ledger.account.Account
import al.rouin.ledger.account.AccountService
import al.rouin.ledger.account.AccountSubType
import al.rouin.ledger.account.AccountType
import al.rouin.ledger.transaction.Transaction
import al.rouin.ledger.transaction.TransactionFetchForm
import al.rouin.ledger.transaction.TransactionQueryForm
import al.rouin.ledger.transaction.TransactionService
import al.rouin.user.UserId
import al.rouin.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LedgerService(
    private val userService: UserService,
    private val accountService: AccountService,
    private val transactionService: TransactionService,
) {
    companion object {
        private val DEFAULT_FETCH_FROM_DATE = LocalDate.of(2021, 1, 1)
    }

    fun getAccounts(userId: UserId): List<Account> = accountService.get(userId)

    fun syncAccounts(userId: UserId) {
        val user = userService.getUser(userId)
        val referenceIdToAccount = accountService.getByReferenceId(userId)
        val fetchedAccount = accountService.fetch(user)
        val notRegisteredAccounts = fetchedAccount
            .filterNot { referenceIdToAccount.containsKey(it.referenceId) }
            .filterNot { it.accountType == AccountType.UNSUPPORTED }
            .filterNot { it.accountSubType == AccountSubType.UNSUPPORTED }
            .toList()
        accountService.register(userId, notRegisteredAccounts)
    }

    fun getTransactions(userId: UserId): List<Transaction> = transactionService.get(userId)

    fun syncTransactions(userId: UserId) {
        val user = userService.getUser(userId)
        val lastTransaction = transactionService.getLastTransaction(userId)
        val fetchFrom = lastTransaction?.date ?: DEFAULT_FETCH_FROM_DATE
        val fetchTo = LocalDate.now()
        val referenceIdToAccount = accountService.getByReferenceId(userId)
        val fetchedTransactions = transactionService.fetch(
            TransactionFetchForm(
                user = user,
                from = fetchFrom,
                to = fetchTo,
                accountReferenceIds = referenceIdToAccount.values.map { it.referenceId }
            )
        )
        val referenceIdToTransaction = transactionService.getByReferenceId(
            TransactionQueryForm(
                userId = userId,
                from = fetchFrom,
                to = fetchTo,
            )
        )
        val notRegisteredTransactions = fetchedTransactions
            .filterNot { referenceIdToTransaction.containsKey(it.transactionReferenceId) }
            .filter { referenceIdToAccount.containsKey(it.accountReferenceId) }
            .associateBy { referenceIdToAccount[it.accountReferenceId]!!.accountId }
        transactionService.register(userId = userId, accountIdToTransaction = notRegisteredTransactions)
    }
}

