package al.rouin.common

import al.rouin.external.ReferenceId
import al.rouin.ledger.account.AccountId
import al.rouin.ledger.budget.BudgetId
import al.rouin.ledger.category.CategoryId
import al.rouin.ledger.exchange.ExchangeRateId
import al.rouin.ledger.transaction.TransactionId
import al.rouin.user.UserId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class AuditEntity {
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @Column(name = "modified_date")
    @LastModifiedDate
    var modifiedAt: LocalDateTime = LocalDateTime.MAX
        private set
}


@Converter(autoApply = true)
class UserIdConverter : AttributeConverter<UserId, String> {
    override fun convertToDatabaseColumn(attribute: UserId?): String? = attribute?.id

    override fun convertToEntityAttribute(dbData: String?): UserId? = dbData?.let { UserId(it) }
}


@Converter(autoApply = true)
class AccountIdConverter : AttributeConverter<AccountId, String> {
    override fun convertToDatabaseColumn(attribute: AccountId?): String? = attribute?.id

    override fun convertToEntityAttribute(dbData: String?): AccountId? = dbData?.let { AccountId(it) }
}


@Converter(autoApply = true)
class TransactionIdConverter : AttributeConverter<TransactionId, String> {
    override fun convertToDatabaseColumn(attribute: TransactionId?): String? = attribute?.id

    override fun convertToEntityAttribute(dbData: String?): TransactionId? = dbData?.let { TransactionId(it) }
}


@Converter(autoApply = true)
class CategoryIdConverter : AttributeConverter<CategoryId, String> {

    override fun convertToDatabaseColumn(attribute: CategoryId?): String? = attribute?.id
    override fun convertToEntityAttribute(dbData: String?): CategoryId? = dbData?.let { CategoryId(it) }
}


@Converter(autoApply = true)
class BudgetIdConverter : AttributeConverter<BudgetId, String> {
    override fun convertToDatabaseColumn(attribute: BudgetId?): String? = attribute?.id

    override fun convertToEntityAttribute(dbData: String?): BudgetId? = dbData?.let { BudgetId(it) }
}


@Converter(autoApply = true)
class ReferenceIdConverter : AttributeConverter<ReferenceId, String> {
    override fun convertToDatabaseColumn(attribute: ReferenceId?): String? = attribute?.id

    override fun convertToEntityAttribute(dbData: String?): ReferenceId? = dbData?.let { ReferenceId(it) }
}


@Converter(autoApply = true)
class ExchangeRateConverter : AttributeConverter<ExchangeRateId, String> {
    override fun convertToDatabaseColumn(attribute: ExchangeRateId?): String? = attribute?.id

    override fun convertToEntityAttribute(dbData: String?): ExchangeRateId? = dbData?.let { ExchangeRateId(it) }
}