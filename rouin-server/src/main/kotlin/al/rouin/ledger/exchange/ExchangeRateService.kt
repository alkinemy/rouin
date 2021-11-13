package al.rouin.ledger.exchange

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import javax.transaction.Transactional

@Service
class ExchangeRateService(
    private val exchangeRateClient: ExchangeRateClient,
    private val exchangeRateRepository: ExchangeRateRepository
) {

    fun fetchExchangeRates(id: ExchangeRateId): Flux<ExchangeRate> {
        val form = ExchangeRateFetchForm.from(id)
        return exchangeRateClient.getExchangeRates(form)
    }

    @Transactional
    fun upsert(exchangeRate: ExchangeRate) {
        exchangeRateRepository.findByExchangeRateId(exchangeRate.exchangeRateId)
            ?.let { it.exchangeRate = exchangeRate.rate }
            ?: let {
                val entity = ExchangeRateEntity.from(exchangeRate)
                exchangeRateRepository.save(entity)
            }
    }
}