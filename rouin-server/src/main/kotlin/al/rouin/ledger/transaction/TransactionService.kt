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

    fun get(userId: UserId): List<Transaction> = transactionRepository.findByUserIdAndDeletedFalse(userId.id)
        .map { it.toModel() }

    fun getLastTransaction(userId: UserId): Transaction? =
        transactionRepository.findByUserIdAndDeletedFalseOrderByIdDesc(userId.id)
            ?.toModel()

    fun getByReferenceId(form: TransactionQueryForm): Map<ReferenceId, Transaction> = with(form) {
        transactionRepository.findByUserIdAndDateGreaterThanEqualAndDateLessThanEqualAndDeletedFalse(
            userId = userId.id,
            from = from,
            to = to,
        ).associateBy(
            { ReferenceId(it.referenceId) },
            { it.toModel() }
        )
    }

    fun fetch(form: TransactionFetchForm): List<TransactionReference> = transactionClient.fetch(form)

    fun register(userId: UserId, accountIdToTransaction: Map<AccountId, TransactionReference>): List<Transaction> {
        val entities = accountIdToTransaction
            .map { TransactionEntity.from(userId = userId, accountId = it.key, transaction = it.value) }
            .toList()
        val saved = transactionRepository.saveAll(entities)
        return saved.map { it.toModel() }
    }
}