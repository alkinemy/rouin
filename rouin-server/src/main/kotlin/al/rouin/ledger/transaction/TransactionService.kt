package al.rouin.ledger.transaction

import al.rouin.external.ReferenceId
import al.rouin.ledger.account.AccountId
import al.rouin.ledger.transaction.client.TransactionClient
import al.rouin.ledger.transaction.client.TransactionEntity
import al.rouin.ledger.transaction.client.TransactionRepository
import al.rouin.user.UserId
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionClient: TransactionClient,
    private val transactionRepository: TransactionRepository,
) {

    fun get(form: TransactionQueryForm): List<Transaction> = form.execute()
        .map { it.toModel() }

    fun getLastTransaction(userId: UserId): Transaction? =
        transactionRepository.findByUserIdAndDeletedFalseOrderByIdDesc(userId)
            ?.toModel()

    fun getByReferenceId(form: TransactionQueryForm): Map<ReferenceId, Transaction> = form.execute()
        .associateBy(
            { it.referenceId },
            { it.toModel() }
        )

    private fun TransactionQueryForm.execute() = transactionRepository.findByUserIdAndDateGreaterThanEqualAndDateLessThanEqualAndDeletedFalse(
        userId = userId,
        from = from.atStartOfDay(),
        to = (to.plusDays(1).atStartOfDay().minusNanos(1)),
    )

    fun fetch(form: TransactionFetchForm): List<TransactionReference> = transactionClient.fetch(form)

    fun register(userId: UserId, accountIdToTransaction: Map<AccountId, TransactionReference>): List<Transaction> {
        val entities = accountIdToTransaction
            .map { TransactionEntity.from(userId = userId, accountId = it.key, transaction = it.value) }
            .toList()
        val saved = transactionRepository.saveAll(entities)
        return saved.map { it.toModel() }
    }
}