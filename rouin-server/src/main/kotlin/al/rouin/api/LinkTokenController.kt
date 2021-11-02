package al.rouin.api

import al.rouin.token.TokenService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class LinkTokenController(
    private val tokenService: TokenService
) {

    @PostMapping("/api/v1/tokens/links")
    fun issueLinkToken(): TokenDto {
        val token = tokenService.issueLinkToken(UUID.randomUUID().toString()) //TODO: set reasonable ID value
        return TokenDto(token = token.token)
    }

}


data class TokenDto(
    val token: String,
)
