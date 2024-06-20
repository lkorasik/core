package ru.urfu.mm.controller.authentication

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import ru.urfu.mm.controller.Endpoints

@RequestMapping(Endpoints.Authentication.BASE)
interface AuthenticationControllerDescription {
    @PostMapping(Endpoints.Authentication.REGISTER)
    fun register(@RequestBody dto: RegistrationDTO): AccessTokenDTO

    @PostMapping(Endpoints.Authentication.LOGIN)
    fun login(@RequestBody dto: LoginDTO): AccessTokenDTO

    @PostMapping(Endpoints.Authentication.VALIDATE_TOKEN)
    fun validateToken(@RequestBody tokenDTO: TokenDTO)
}