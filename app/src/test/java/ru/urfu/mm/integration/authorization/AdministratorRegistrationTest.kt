package ru.urfu.mm.integration.authorization

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.urfu.mm.RestAssureExtension.cast
import ru.urfu.mm.RestAssureExtension.whenever
import ru.urfu.mm.controller.Endpoints
import ru.urfu.mm.controller.ExceptionDTO
import ru.urfu.mm.controller.authentication.AccessTokenDTO
import ru.urfu.mm.controller.authentication.RegistrationDTO
import ru.urfu.mm.domain.enums.UserRole
import ru.urfu.mm.dsl.AuthorizationDSL
import ru.urfu.mm.dsl.DSL
import ru.urfu.mm.dsl.RegistrationTokenFactory
import ru.urfu.mm.integration.BaseTestClass
import ru.urfu.mm.persistance.entity.enums.UserEntityRole
import ru.urfu.mm.persistance.repository.AccountRepository
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository
import java.util.*

class `Administrator registration` : BaseTestClass() {
    @Autowired
    private lateinit var registrationTokenRepository: RegistrationTokenRepository
    @Autowired
    private lateinit var accountRepository: AccountRepository
    @Autowired
    private lateinit var authorizationDSL: AuthorizationDSL

    @AfterEach
    fun clean() {
        registrationTokenRepository.deleteAll()
        accountRepository.deleteAll()
    }

    /**
     * Регистрация администратора
     */
    @Test
    fun `Register administrator`() {
        val password = DSL.generatePassword()

        val registrationToken = RegistrationTokenFactory.build()
        registrationTokenRepository.save(registrationToken)

        val expected = AccessTokenDTO("", registrationToken.registrationToken.toString(), UserRole.ADMIN.value)

        val registrationDTO = RegistrationDTO(registrationToken.registrationToken.toString(), password, password)

        val actual = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(registrationDTO)
            .whenever()
            .baseUri(address())
            .post(Endpoints.Authentication.register())
            .then()
            .statusCode(200)
            .extract()
            .cast(AccessTokenDTO::class.java)

        Assertions.assertNotNull(actual.accessToken)
        Assertions.assertEquals(actual.userEntityRole, expected.userEntityRole)
        Assertions.assertEquals(actual.userToken, expected.userToken)

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty())
        Assertions.assertEquals(1, accountRepository.findAll().size)

        val account = accountRepository.findAll().stream().findFirst().get()
        Assertions.assertEquals(account.login, registrationToken.registrationToken)
        Assertions.assertFalse(account.password.isEmpty())
        Assertions.assertEquals(account.role, UserEntityRole.ADMIN)
    }

    /**
     * Регистрация администратора. Токен не добавлен в базу данных.
     */
    @Test
    fun `No token`() {
        val token = UUID.randomUUID()
        val password = DSL.generatePassword()

        val registrationDTO = RegistrationDTO(token.toString(), password, password)

        val actual = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(registrationDTO)
            .whenever()
            .baseUri(address())
            .post(Endpoints.Authentication.register())
            .then()
            .statusCode(400)
            .extract()
            .cast(ExceptionDTO::class.java)

        Assertions.assertEquals(actual.message, "Registration token $token does not exist")

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty())
        Assertions.assertTrue(accountRepository.findAll().isEmpty())
    }
}
