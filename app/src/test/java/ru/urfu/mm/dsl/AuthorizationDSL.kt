package ru.urfu.mm.dsl

import org.springframework.web.client.RestTemplate
import ru.urfu.mm.controller.Endpoints
import ru.urfu.mm.controller.authentication.AccessTokenDTO
import ru.urfu.mm.controller.authentication.RegistrationDTO
import java.net.URI

/**
 * Класс, предоставляющий операции для работы с приложением
 */
object AuthorizationDSL {
    fun registerAsAdministratorAccount(registrationDTO: RegistrationDTO, address: String): AccessTokenDTO {
        val client = RestTemplate()
        val uri = URI.create(address + Endpoints.Authentication.register())
        val postForEntity = client.postForEntity(uri, registrationDTO, AccessTokenDTO::class.java)
        return postForEntity.body!!
    }
}