package al.rouin.config.external.plaid

import com.plaid.client.ApiClient
import com.plaid.client.request.PlaidApi
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(PlaidProperties::class)
class PlaidConfig(
    private val plaidProperties: PlaidProperties
) {
    @Bean
    fun plaidApi(): PlaidApi {
        val apiClient = ApiClient(
            mapOf(
                "clientId" to plaidProperties.clientId,
                "secret" to plaidProperties.secret,
                "plaidVersion" to plaidProperties.version
            )
        )
        apiClient.setPlaidAdapter(plaidProperties.environment.endpoint)
        return apiClient.createService(PlaidApi::class.java)
    }
}