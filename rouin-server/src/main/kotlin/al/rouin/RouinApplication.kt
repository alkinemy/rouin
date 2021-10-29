package al.rouin

import al.rouin.config.RouinConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(RouinConfig::class)
@SpringBootApplication
class RouinApplication

fun main(args: Array<String>) {
    runApplication<RouinApplication>(*args)
}