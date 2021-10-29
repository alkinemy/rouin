package al.rouin.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("rouin.plaid")
data class PlaidProperties(
    val clientId: String,
    val secret: String,
    val version: String,
    val environment: PlaidEnvironment,
)