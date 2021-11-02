package al.rouin.api

import al.rouin.token.TokenService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class TokenController(
    private val tokenService: TokenService
) {

    @PostMapping("/api/v1/tokens")
    fun issue(): TokenDto {
        val token = tokenService.issue(UUID.randomUUID().toString()) //TODO: set reasonable ID value
        return TokenDto(token = token.token)
    }

}


data class TokenDto(
    val token: String,
)
