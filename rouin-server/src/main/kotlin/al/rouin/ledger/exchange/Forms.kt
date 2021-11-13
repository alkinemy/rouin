package al.rouin.ledger.exchange

data class ExchangeRateFetchForm(
    val from: CurrencyCode,
    val to: CurrencyCode
) {
    companion object {
        fun from(id: ExchangeRateId) = ExchangeRateFetchForm(from = id.from, to = id.to)
    }
}
