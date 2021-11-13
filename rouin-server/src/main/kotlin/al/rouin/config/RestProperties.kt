package al.rouin.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("rouin.rest")
data class RestProperties(
    val connectTimeoutInMilliseconds: Long,
    val readTimeoutInMilliseconds: Long,
    val writeTimeoutInMilliseconds: Long,
)