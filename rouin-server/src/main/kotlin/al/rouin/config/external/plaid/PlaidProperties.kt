package al.rouin.config.external.plaid

import com.plaid.client.ApiClient
import com.plaid.client.model.CountryCode
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("rouin.external.plaid")
data class PlaidProperties(
    val clientId: String,
    val secret: String,
    val version: String,
    val environment: PlaidEnvironment,
    val countryCode: List<PlaidCountryCode>,
    val language: String,
    val redirectUri: String?,
)


enum class PlaidEnvironment(val endpoint: String) {
    SANDBOX(ApiClient.Sandbox),
    DEVELOPMENT(ApiClient.Development),
    PRODUCTION(ApiClient.Production)
}


enum class PlaidCountryCode(val countryCode: CountryCode) {
    CA(CountryCode.CA)
}