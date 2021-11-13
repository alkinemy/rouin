package al.rouin.ledger.exchange

import al.rouin.IntegrationTest
import al.rouin.common.logger
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
internal class ExchangeRateClientTest(
    @Autowired private val exchangeRateClient: ExchangeRateClient,
) {
    private val log = this.logger()

    @Test
    @Disabled("Quota limit")
    fun test() {
        exchangeRateClient.getExchangeRates(ExchangeRateFetchForm(from = CurrencyCode.CAD, to = CurrencyCode.KRW))
            .doOnEach { log.debug(it.toString()) }
            .blockLast()
    }
}