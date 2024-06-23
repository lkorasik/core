package ru.urfu.mm.dsl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.urfu.mm.TestConfiguration
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
class AuthorizationDSL @Autowired constructor(
    private val registrationTokenRepository: RegistrationTokenRepository,
    private val configuration: TestConfiguration
) {
    fun registerAsAdministratorAccount(registrationDTO: RegistrationDTO): AccessTokenDTO {
        val client = RestTemplate()
        val uri = URI.create(configuration.address() + Endpoints.Authentication.register())
        val postForEntity = client.postForEntity(uri, registrationDTO, AccessTokenDTO::class.java)
        return postForEntity.body!!
    }

    fun registerAdministratorAccount(): String {
        val token = prepareDatabase()

        val password = UUID.randomUUID().toString().replace("-", "")
        val dto = RegistrationDTO(token, password, password)

        val client = RestTemplate()
        val uri = URI.create(configuration.address() + Endpoints.Authentication.register())
        val postForEntity = client.postForEntity(uri, dto, AccessTokenDTO::class.java)

        return postForEntity.body!!.accessToken
    }

    /**
     * Подготовить базу данных для регистрации. Вставить токен.
     */
    fun prepareDatabase(): UUID {
        val registrationToken = RegistrationTokenFactory.build()
        registrationTokenRepository.save(registrationToken)
        return registrationToken.registrationToken
    }
}