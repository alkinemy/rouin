package al.rouin.ledger

import al.rouin.common.AccountNotFoundException
import al.rouin.common.CategoryNotFoundException
import al.rouin.common.getOrThrow
import al.rouin.ledger.account.*
import al.rouin.ledger.category.Category
import al.rouin.ledger.category.CategoryId
import al.rouin.ledger.category.CategoryService
import al.rouin.ledger.transaction.*
import al.rouin.user.UserId
import al.rouin.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

@Service
class LedgerService(
    private val userService: UserService,
    private val accountService: AccountService,
    private val categoryService: CategoryService,
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

    fun getRichTransactions(userId: UserId, yearMonth: YearMonth): List<RichTransaction> {
        val transactions = transactionService.get(
            TransactionQueryForm(
                userId = userId,
                from = yearMonth.atDay(1),
                to = yearMonth.atEndOfMonth(),
            )
        )
        val accountIdToAccount = getTransactionAccounts(userId = userId, transactions = transactions)
        val categoryIdToCategory = getTransactionCategories(transactions, userId)
        return transactions
            .map {
                RichTransaction.fromTransaction(
                    transaction = it,
                    account = accountIdToAccount.getOrThrow(it.accountId) { AccountNotFoundException("Account for id ${it.accountId} doesn't exist") },
                    category = categoryIdToCategory.getOrThrow(it.categoryId) { CategoryNotFoundException("Category for id ${it.categoryId} doesn't exist") }
                )
            }
    }

    private fun getTransactionAccounts(userId: UserId, transactions: List<Transaction>): Map<AccountId, Account> {
        val accountIds = transactions
            .map { it.accountId }
            .toSet()
        return accountService.getByAccountId(userId = userId, accountIds = accountIds)
    }

    private fun getTransactionCategories(transactions: List<Transaction>, userId: UserId): Map<CategoryId, Category> {
        val categoryIds = transactions
            .map { it.categoryId }
            .toSet()
        return categoryService.getByCategoryId(userId = userId, categoryIds = categoryIds)
    }

    fun syncTransactions(userId: UserId) {
        val lastTransaction = transactionService.getLastTransaction(userId)
        val fetchFrom = lastTransaction?.date?.toLocalDate() ?: DEFAULT_FETCH_FROM_DATE
        val fetchTo = LocalDate.now()
        val user = userService.getUser(userId)
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

