package al.rouin.ledger.exchange

import al.rouin.common.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ExchangeRateScheduledService(
    private val exchangeRateService: ExchangeRateService,
) {

    private val log = this.logger()

    @Scheduled(cron = "0 30 */1 * * *")
    fun updateExchangeRate() {
        combineCurrencyCodes()
            .map { exchangeRateService.fetchExchangeRates(it) }
            .reduce { acc, flux -> acc.concatWith(flux) }
            .subscribe {
                exchangeRateService.upsert(it)
                log.debug("[EXCHANGE-RATE] exchange rate updated: ${it.exchangeRateId} -> ${it.rate}")
            }
    }

    private fun combineCurrencyCodes(): List<ExchangeRateId> {
        val currencyCodes = CurrencyCode.values()
        return currencyCodes.indices
            .flatMap { i -> currencyCodes.indices.minus(0..i)
                .map { j -> ExchangeRateId.of(from = currencyCodes[i], to = currencyCodes[j]) }
            }
    }

}