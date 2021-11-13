package al.rouin.ledger.exchange

import al.rouin.common.RestClientException
import al.rouin.common.RestServerException
import al.rouin.config.external.exchange.ExchangeRateProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Service
class ExchangeRateClient(
    private val exchangeRateProperties: ExchangeRateProperties,
    private val webClient: WebClient,
) {
    fun getExchangeRates(form: ExchangeRateFetchForm): Flux<ExchangeRate> = webClient.get()
        .uri(exchangeRateProperties.baseUrl) {
            it.path("/api/v7/convert")
                .queryParam("q", form.toQueryParams())
                .queryParam("apiKey", exchangeRateProperties.apiKey)
                .build()
        }
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError) {
            it.createException().map { e -> RestClientException("Fail to execute", e) }
        }
        .onStatus(HttpStatus::is5xxServerError) {
            it.createException().map { e -> RestServerException("Fail to execute", e) }
        }
        .bodyToMono(ExchangeRateDto::class.java)
        .flatMapIterable {
            it.results.values.map { dto -> dto.toModel() }
        }

    private fun ExchangeRateFetchForm.toQueryParams() = "${ExchangeRateId.of(from = from, to = to)},${ExchangeRateId.of(from = to, to = from)}"
}


data class ExchangeRateDto(
    val results: Map<ExchangeRateId, ExchangeRateResultDto>
)


data class ExchangeRateResultDto(
    val id: ExchangeRateId,
    @JsonProperty("fr") val from: CurrencyCode,
    val to: CurrencyCode,
    @JsonProperty("val")
    val rate: Double
) {
    fun toModel() = ExchangeRate(
        from = from,
        to = to,
        rate = rate
    )
}