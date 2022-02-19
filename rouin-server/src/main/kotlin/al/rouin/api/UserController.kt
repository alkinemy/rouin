package al.rouin.api

import al.rouin.ledger.LedgerService
import al.rouin.token.PublicToken
import al.rouin.user.User
import al.rouin.user.UserId
import al.rouin.user.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService,
    private val ledgerService: LedgerService,
    @Value("\${rouin.user.email}") private val email: String,
    @Value("\${rouin.user.user-id}") private val userId: String,
) {

    @PostMapping("/api/v1/users/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody dto: SignUpDto) {
        userService.create(email = dto.email)
    }

    @PostMapping("/api/v1/users/sign-in")
    fun signIn(@RequestBody dto: SignInDto): UserDto {
        //TODO implement sign-in process
        return UserDto(
            userId = UserId(userId),
            email = email,
        )
    }

    @PostMapping("/api/v1/users/{userId}/tokens")
    fun issueLinkToken(@PathVariable userId: UserId): TokenDto {
        val token = userService.issueLinkToken(userId)
        return TokenDto(token = token.token)
    }

    @PostMapping("/api/v1/ledgers/{userId}/banks")
    fun linkBank(@PathVariable userId: UserId, @RequestBody dto: BankLinkDto) {
        ledgerService.linkBank(
            userId = userId,
            token = PublicToken(dto.token)
        )
    }
}


data class SignUpDto(
    val email: String,
)


data class SignInDto(
    val email: String,
)


data class UserDto(
    val userId: UserId,
    val email: String,
) {
    companion object {
        fun from(model: User) = UserDto(
            userId = model.userId,
            email = model.email,
        )
    }
}


data class TokenDto(
    val token: String,
)


data class BankLinkDto(
    val token: String,
)
