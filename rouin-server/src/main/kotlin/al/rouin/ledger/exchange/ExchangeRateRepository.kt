package al.rouin.ledger.exchange

import al.rouin.common.RouinRepository
import org.springframework.stereotype.Repository

@Repository
interface ExchangeRateRepository : RouinRepository<ExchangeRateEntity, Long> {
    fun findByExchangeRateId(exchangeRateId: ExchangeRateId): ExchangeRateEntity?
}
