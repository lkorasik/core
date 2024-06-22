package ru.urfu.mm.dsl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.urfu.mm.controller.Endpoints
import ru.urfu.mm.controller.authentication.AccessTokenDTO
import ru.urfu.mm.controller.authentication.RegistrationDTO
import ru.urfu.mm.persistance.entity.RegistrationToken
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository
import java.net.URI
import java.util.UUID

/**
 * Класс, предоставляющий операции для работы с приложением
 */
@Component
class AuthorizationDSL @Autowired constructor(private val registrationTokenRepository: RegistrationTokenRepository) {
    fun registerAsAdministratorAccount(registrationDTO: RegistrationDTO, address: String): AccessTokenDTO {
        val client = RestTemplate()
        val uri = URI.create(address + Endpoints.Authentication.register())
        val postForEntity = client.postForEntity(uri, registrationDTO, AccessTokenDTO::class.java)
        return postForEntity.body!!
    }

    fun registerAdministratorAccount(address: String): String {
        val token = UUID.randomUUID()

        val registrationToken = RegistrationToken(token)
        registrationTokenRepository.save(registrationToken)

        val password = UUID.randomUUID().toString().replace("-", "")
        val dto = RegistrationDTO(token, password, password)

        val client = RestTemplate()
        val uri = URI.create(address + Endpoints.Authentication.register())
        val postForEntity = client.postForEntity(uri, dto, AccessTokenDTO::class.java)

        return postForEntity.body!!.accessToken
    }
}