package al.rouin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping(path = ["/hello"])
    fun hello() = "Hello Rouin!"
    
}