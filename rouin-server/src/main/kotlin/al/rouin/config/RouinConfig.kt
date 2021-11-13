package al.rouin.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit.MILLISECONDS


@EnableJpaAuditing
@EnableScheduling
@Configuration
@EnableConfigurationProperties(RestProperties::class)
class RouinConfig {
    @Bean
    fun webClient(restProperties: RestProperties): WebClient {
        val httpClient: HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, restProperties.connectTimeoutInMilliseconds.toInt())
            .doOnConnected { connection ->
                connection.addHandlerLast(ReadTimeoutHandler(restProperties.readTimeoutInMilliseconds, MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(restProperties.writeTimeoutInMilliseconds, MILLISECONDS))
            }
        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }
}