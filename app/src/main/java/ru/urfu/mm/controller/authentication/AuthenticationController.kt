package ru.urfu.mm.controller.authentication

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import ru.urfu.mm.application.usecase.create.account.CreateAccount
import ru.urfu.mm.application.usecase.create.account.CreateAccountRequest
import ru.urfu.mm.application.usecase.login_user.LoginRequest
import ru.urfu.mm.application.usecase.login_user.LoginUser
import ru.urfu.mm.service.AuthenticationService
import java.util.*

@RestController
class AuthenticationController @Autowired constructor(
    private val authenticationService: AuthenticationService,
    private val createAccount: CreateAccount,
    private val loginUser: LoginUser
) : AuthenticationControllerDescription {
    override fun register(dto: RegistrationDTO): AccessTokenDTO {
        val request = object : CreateAccountRequest {
            override fun getToken(): UUID = dto.token

            override fun getPassword(): String = dto.password

            override fun getPasswordAgain(): String = dto.passwordAgain
        }
        val role = createAccount.createUser(request)
        val token = authenticationService.generateToken(dto)

        return AccessTokenDTO(token, dto.token.toString(), role.value)
    }

    override fun login(dto: LoginDTO): AccessTokenDTO {
        val request = object  : LoginRequest{
            override fun getToken(): UUID = dto.token
            override fun getPassword(): String = dto.password
        }
        val account = loginUser.loginUser(request)
        val token = authenticationService.generateToken(dto)

        return AccessTokenDTO(token, dto.token.toString(), account.role.value)
    }
}
