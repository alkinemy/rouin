package al.rouin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RouinApplication

fun main(args: Array<String>) {
    runApplication<RouinApplication>(*args)
}