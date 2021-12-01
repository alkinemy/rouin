package al.rouin.api

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
    @Value("\${rouin.user.email}") private val email: String,
    @Value("\${rouin.user.user-id}") private val userId: String,
) {
    @PostMapping("/api/v1/users/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody signUpDto: SignUpDto) {
        userService.create(email = signUpDto.email)
    }

    @PostMapping("/api/v1/users/sign-in")
    fun signIn(@RequestBody signInDto: SignInDto): UserDto {
        //TODO implement sign-in process
        return UserDto(
            userId = UserId(userId),
            email = email,
        )
    }

    @PostMapping("/api/v1/users/{userId}/tokens")
    fun registerToken(@PathVariable userId: UserId, @RequestBody tokenDto: PublicTokenDto) {
        userService.registerToken(
            userId = userId,
            token = PublicToken(tokenDto.token)
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
)


data class PublicTokenDto(
    val token: String
)