package al.rouin.ledger.exchange

import al.rouin.IntegrationTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
class ExchangeRateScheduledServiceTest(
    @Autowired private val exchangeRateScheduledService: ExchangeRateScheduledService,
) {
    @Test
    @Disabled("Quota limit")
    fun updateExchangeRates() {
        exchangeRateScheduledService.updateExchangeRate()
        Thread.sleep(2000)
    }
}