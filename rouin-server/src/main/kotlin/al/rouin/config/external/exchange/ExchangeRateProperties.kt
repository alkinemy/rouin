package al.rouin.config.external.exchange

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("rouin.external.exchange-rate")
data class ExchangeRateProperties(
    val baseUrl: String,
    val apiKey: String,
)