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
import ru.urfu.mm.controller.authentication.LoginDTO
import ru.urfu.mm.controller.authentication.RegistrationDTO
import ru.urfu.mm.domain.enums.UserRole
import ru.urfu.mm.dsl.AuthorizationDSL
import ru.urfu.mm.dsl.DSL
import ru.urfu.mm.integration.BaseTestClass
import ru.urfu.mm.persistance.entity.RegistrationToken
import ru.urfu.mm.persistance.repository.AccountRepository
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository
import java.util.*

class `Administrator login` : BaseTestClass() {
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
     * Вход в систему администратором.
     */
    @Test
    fun `Login administrator`() {
        val token = UUID.randomUUID()
        val password = DSL.generatePassword()

        val registrationToken = RegistrationToken(token)
        registrationTokenRepository.save(registrationToken)

        val expected = AccessTokenDTO("", token.toString(), UserRole.ADMIN.value)

        val registrationDTO = RegistrationDTO(token.toString(), password, password)
        authorizationDSL.registerAsAdministratorAccount(registrationDTO, address())

        val loginDTO = LoginDTO(token.toString(), password)

        val actual = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(loginDTO)
            .whenever()
            .baseUri(address())
            .post(Endpoints.Authentication.login())
            .then()
            .statusCode(200)
            .extract()
            .cast(AccessTokenDTO::class.java)

        Assertions.assertNotNull(actual.accessToken)
        Assertions.assertEquals(actual.userEntityRole, expected.userEntityRole)
        Assertions.assertEquals(actual.userToken, expected.userToken)

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty())
        Assertions.assertEquals(1, accountRepository.findAll().size)
    }

    /**
     * Вход в систему администратором. Неверный токен.
     */
    @Test
    fun `Token invalid`() {
        val token = UUID.randomUUID()
        val password = DSL.generatePassword()

        val registrationToken = RegistrationToken(token)
        registrationTokenRepository.save(registrationToken)

        val registrationDTO = RegistrationDTO(token.toString(), password, password)
        authorizationDSL.registerAsAdministratorAccount(registrationDTO, address())

        val loginDTO = LoginDTO(UUID.randomUUID().toString(), password)

        val actual2 = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(loginDTO)
            .whenever()
            .baseUri(address())
            .post(Endpoints.Authentication.login())
            .then()
            .statusCode(400)
            .extract()
            .cast(ExceptionDTO::class.java)

        Assertions.assertEquals(
            "Invalid credentials. Please check your token and password.",
            actual2.message
        )

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty())
        Assertions.assertEquals(1, accountRepository.findAll().size)
    }

    /**
     * Вход в систему администратором. Неверный пароль.
     */
    @Test
    fun `Incorrect password`() {
        val token = UUID.randomUUID()
        val password = DSL.generatePassword()

        val registrationToken = RegistrationToken(token)
        registrationTokenRepository.save(registrationToken)

        val registrationDTO = RegistrationDTO(token.toString(), password, password)
        authorizationDSL.registerAsAdministratorAccount(registrationDTO, address())

        val loginDTO = LoginDTO(token.toString(), UUID.randomUUID().toString())

        val actual2 = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(loginDTO)
            .whenever()
            .baseUri(address())
            .post(Endpoints.Authentication.login())
            .then()
            .statusCode(400)
            .extract()
            .cast(ExceptionDTO::class.java)

        Assertions.assertEquals("Invalid credentials. Please check your token and password.", actual2.message)

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty())
        Assertions.assertTrue(accountRepository.findAll().any { it.login.equals(token) })
    }
}
