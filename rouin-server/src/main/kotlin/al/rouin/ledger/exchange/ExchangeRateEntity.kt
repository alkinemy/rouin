package al.rouin.ledger.exchange

import al.rouin.common.AuditEntity
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "exchange_rates")
data class ExchangeRateEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(name = "exchange_rate_id")
    val exchangeRateId: ExchangeRateId,
    @Column(name = "from_currency")
    @Enumerated(STRING)
    val from: CurrencyCode,
    @Column(name = "to_currency")
    @Enumerated(STRING)
    val to: CurrencyCode,
    @Column(name = "rate")
    var exchangeRate: Double
) : AuditEntity() {
    companion object {
        fun from(model: ExchangeRate) = with(model) {
            ExchangeRateEntity(
                exchangeRateId = exchangeRateId,
                from = from,
                to = to,
                exchangeRate = rate,
            )
        }
    }
}