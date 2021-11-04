package al.rouin.api

import al.rouin.common.UserId
import al.rouin.token.PublicToken
import al.rouin.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/api/v1/users/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody signUpDto: SignUpDto) {
        userService.create(email = signUpDto.email)
    }

    @PostMapping("/api/v1/users/sign-in")
    fun signIn(@RequestBody signInDto: SignInDto): UserDto {
        TODO()
    }

    @PostMapping("/api/v1/{userId}/tokens")
    fun registerToken(@PathVariable userId: String, @RequestBody tokenDto: PublicTokenDto) {
        userService.registerToken(
            userId = UserId.id(userId),
            token = PublicToken(token = tokenDto.token)
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
    val email: String,
    val userId: String
)


data class PublicTokenDto(
    val token: String
)