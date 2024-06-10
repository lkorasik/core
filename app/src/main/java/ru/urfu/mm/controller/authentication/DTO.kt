package ru.urfu.mm.controller.authentication

import ru.urfu.mm.application.usecase.create.account.CreateAccountRequest
import ru.urfu.mm.application.usecase.login_user.LoginRequest
import java.util.*

data class AccessTokenDTO(
    val accessToken: String,
    val userToken: String,
    val userEntityRole: String
)

data class LoginDTO(
    val token: String,
    val password: String
) : LoginRequest {
    override fun getToken(): UUID = UUID.fromString(token)
    override fun getPassword(): String = password
}

data class RegistrationDTO(
    val token: String,
    val password: String,
    val passwordAgain: String
) : CreateAccountRequest {
    override fun getToken(): UUID = UUID.fromString(token)
    override fun getPassword(): String = password
    override fun getPasswordAgain(): String = passwordAgain
}

data class TokenDTO(
    val token: String
)
