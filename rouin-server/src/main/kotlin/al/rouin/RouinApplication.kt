package al.rouin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class RouinApplication

fun main(args: Array<String>) {
    runApplication<RouinApplication>(*args)
}