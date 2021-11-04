package al.rouin.ledger.transaction

import al.rouin.common.AccountId
import al.rouin.common.ReferenceId
import al.rouin.common.UserId
import al.rouin.ledger.Transaction
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionClient: TransactionClient,
    private val transactionRepository: TransactionRepository,
) {

    fun getTransactions(userId: UserId): List<Transaction> = transactionRepository.findByUserId(userId.id)
        .map { it.toModel() }

    fun getByReferenceId(userId: UserId): Map<ReferenceId, Transaction> =
        transactionRepository.findByUserId(userId.id)
            .associateBy(
                { ReferenceId.id(it.referenceId) },
                { it.toModel() }
            )

    fun fetch(form: TransactionForm): List<TransactionReference> = transactionClient.fetch(form)

    fun register(userId: UserId, accountIdToTransaction: Map<AccountId, TransactionReference>): List<Transaction> {
        val entities = accountIdToTransaction
            .map { TransactionEntity.from(userId = userId, accountId = it.key, transaction = it.value) }
            .toList()
        val saved = transactionRepository.saveAll(entities)
        return saved.map { it.toModel() }
    }
}