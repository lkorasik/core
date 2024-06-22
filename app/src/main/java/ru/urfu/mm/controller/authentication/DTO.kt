package ru.urfu.mm.controller.authentication

import io.swagger.v3.oas.annotations.media.Schema

@JvmRecord
data class AccessTokenDTO(
    val accessToken: String,
    val userToken: String,
    val userEntityRole: String
)

@Schema(description = "Для регистрации аккаунта")
@JvmRecord
data class LoginDTO(
    @Schema(description = "Токен", example = "62868339-1019-43d7-a7a9-df00c06022e3")
    val token: String,
    @Schema(description = "Пароль", example = "123456789")
    val password: String
)

@Schema(description = "Для регистрации аккаунта")
@JvmRecord
data class RegistrationDTO(
    @Schema(description = "Токен", example = "62868339-1019-43d7-a7a9-df00c06022e3")
    val token: String,
    @Schema(description = "Пароль", example = "123456789")
    val password: String,
    @Schema(description = "Повтор пароля", example = "123456789")
    val passwordAgain: String
)

@JvmRecord
data class TokenDTO(
    val token: String
)
