package al.rouin.ledger.transaction

import al.rouin.external.ReferenceId
import al.rouin.ledger.account.AccountId
import al.rouin.user.UserId
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionClient: TransactionClient,
    private val transactionRepository: TransactionRepository,
) {

    fun get(userId: UserId): List<Transaction> = transactionRepository.findByUserIdAndDeletedFalse(userId.id)
        .map { it.toModel() }

    fun getByReferenceId(userId: UserId): Map<ReferenceId, Transaction> =
        transactionRepository.findByUserIdAndDeletedFalse(userId.id)
            .associateBy(
                { ReferenceId(it.referenceId) },
                { it.toModel() }
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