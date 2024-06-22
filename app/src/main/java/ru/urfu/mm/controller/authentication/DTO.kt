package ru.urfu.mm.controller.authentication

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
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

@Schema(description = "Для регистрации аккаунта")
@JvmRecord
data class RegistrationDTO(
    @Schema(description = "Токен", example = "62868339-1019-43d7-a7a9-df00c06022e3")
    val token: String,
    @Schema(description = "Пароль", example = "123456789")
    val password: String,
    @Schema(description = "Повтор пароля", example = "123456789")
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
