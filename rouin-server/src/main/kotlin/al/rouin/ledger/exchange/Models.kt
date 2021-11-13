package al.rouin.ledger.exchange

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

data class ExchangeRateId @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
    val id: String
) {
    val from: CurrencyCode = CurrencyCode.valueOf(split()[0])
    val to: CurrencyCode = CurrencyCode.valueOf(split()[1])

    companion object {
        private const val DELIMITER = "_"

        fun of(from: CurrencyCode, to: CurrencyCode) = ExchangeRateId("${from}${DELIMITER}${to}")
    }

    private fun split() = id.split(DELIMITER)

    @JsonValue
    override fun toString() = id
}


data class ExchangeRate(
    val from: CurrencyCode,
    val to: CurrencyCode,
    val rate: Double
) {
    val exchangeRateId: ExchangeRateId = ExchangeRateId.of(from = from, to = to)
}