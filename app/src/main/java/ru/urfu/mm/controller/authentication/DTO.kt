package ru.urfu.mm.controller.authentication

import ru.urfu.mm.application.usecase.create.account.CreateAccountRequest
import ru.urfu.mm.application.usecase.login_user.LoginRequest
import ru.urfu.mm.controller.ToRequest
import java.util.*

@JvmRecord
data class AccessTokenDTO(
    val accessToken: String,
    val userToken: String,
    val userEntityRole: String
)

@JvmRecord
data class LoginDTO(
    val token: String,
    val password: String
) : ToRequest<LoginRequest> {
    override fun toRequest(): LoginRequest {
        return LoginRequest(UUID.fromString(token), password)
    }
}

@JvmRecord
data class RegistrationDTO(
    val token: String,
    val password: String,
    val passwordAgain: String
) : ToRequest<CreateAccountRequest> {
    override fun toRequest(): CreateAccountRequest {
        return CreateAccountRequest(UUID.fromString(token), password, passwordAgain)
    }
}

@JvmRecord
data class TokenDTO(
    val token: String
)
