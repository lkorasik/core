package ru.urfu.mm.controller.authentication

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import ru.urfu.mm.application.usecase.create.account.CreateAccount
import ru.urfu.mm.application.usecase.login_user.LoginUser
import ru.urfu.mm.service.AuthenticationService

@RestController
class AuthenticationController @Autowired constructor(
    private val authenticationService: AuthenticationService,
    private val createAccount: CreateAccount,
    private val loginUser: LoginUser
) : AuthenticationControllerDescription {
    override fun register(dto: RegistrationDTO): AccessTokenDTO {
        val role = createAccount.createUser(dto.toRequest())
        val token = authenticationService.generateToken(dto)

        return AccessTokenDTO(token, dto.token, role.value)
    }

    override fun login(dto: LoginDTO): AccessTokenDTO {
        val account = loginUser.loginUser(dto.toRequest())
        val token = authenticationService.generateToken(dto)

        return AccessTokenDTO(token, dto.token, account.role.value)
    }

    override fun validateToken(tokenDTO: TokenDTO) {
        authenticationService.validateToken(tokenDTO.token)
    }
}
