package ru.urfu.mm.controller.authentication

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.urfu.mm.application.usecase.create.account.CreateAccount
import ru.urfu.mm.application.usecase.login_user.LoginUser
import ru.urfu.mm.controller.Endpoints
import ru.urfu.mm.service.AuthenticationService

@RestController
@RequestMapping(Endpoints.Authentication.BASE)
class AuthenticationController @Autowired constructor(
    private val authenticationService: AuthenticationService,
    private val createAccount: CreateAccount,
    private val loginUser: LoginUser
) {
    @PostMapping(Endpoints.Authentication.REGISTER)
    fun register(@RequestBody dto: RegistrationDTO): AccessTokenDTO {
        val role = createAccount.createUser(dto)
        val token = authenticationService.generateToken(dto)

        return AccessTokenDTO(token, dto.token, role.value)
    }

    @PostMapping(Endpoints.Authentication.LOGIN)
    fun login(@RequestBody dto: LoginDTO): AccessTokenDTO {
        val account = loginUser.loginUser(dto)
        val token = authenticationService.generateToken(dto)

        return AccessTokenDTO(token, dto.token, account.role.value)
    }

    @PostMapping(Endpoints.Authentication.VALIDATE_TOKEN)
    fun validateToken(@RequestBody tokenDTO: TokenDTO) {
        authenticationService.validateToken(tokenDTO.token)
    }
}
