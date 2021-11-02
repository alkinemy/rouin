package al.rouin.api

import al.rouin.token.PublicToken
import al.rouin.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/users/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody signUpDto: SignUpDto) {
        userService.create(
            email = signUpDto.email,
            publicToken = PublicToken(
                token = signUpDto.publicToken
            )
        )
    }

    @PostMapping("/api/v1/users/sign-in")
    fun signIn(@RequestBody signInDto: SignInDto): UserDto {
        TODO()
    }

}


data class SignUpDto(
    val email: String,
    val publicToken: String,
)


data class SignInDto(
    val email: String,
)


data class UserDto(
    val email: String,
)