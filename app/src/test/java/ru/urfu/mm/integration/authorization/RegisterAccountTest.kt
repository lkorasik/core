//package ru.urfu.mm.integration.authorization
//
//import io.restassured.RestAssured
//import io.restassured.http.ContentType
//import org.junit.jupiter.api.AfterEach
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import ru.urfu.mm.controller.Endpoints.Authentication.register
//import ru.urfu.mm.controller.ExceptionDTO
//import ru.urfu.mm.controller.authentication.AccessTokenDTO
//import ru.urfu.mm.controller.authentication.RegistrationDTO
//import ru.urfu.mm.domain.enums.UserRole
//import ru.urfu.mm.dsl.DSL
//import ru.urfu.mm.integration.BaseTestClass
//import ru.urfu.mm.persistance.entity.RegistrationToken
//import ru.urfu.mm.persistance.entity.enums.UserEntityRole
//import ru.urfu.mm.persistance.repository.AccountRepository
//import ru.urfu.mm.persistance.repository.RegistrationTokenRepository
//import java.util.*
//
//class `Register account` : BaseTestClass() {
//    @Autowired
//    private lateinit var registrationTokenRepository: RegistrationTokenRepository
//
//    @Autowired
//    private lateinit var accountRepository: AccountRepository
//
//    @AfterEach
//    fun clean() {
//        registrationTokenRepository.deleteAll()
//        accountRepository.deleteAll()
//    }
//
//    /**
//     * Регистрация аккаунта. Пароли в регистрационной форме не совпадают.
//     */
//    @Test
//    fun `Passwords not same`() {
//        val token = UUID.randomUUID()
//        val password = DSL.generatePassword()
//        val passwordAgain = DSL.generatePassword()
//
//        val registrationToken = RegistrationToken(token)
//        registrationTokenRepository.save(registrationToken)
//
//        val registrationDTO = RegistrationDTO(token.toString(), password, passwordAgain)
//
//        val actual = RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(registrationDTO)
//            .`when`()
//            .baseUri(address())
//            .post(register())
//            .then()
//            .statusCode(400)
//            .extract()
//            .`as`(ExceptionDTO::class.java)
//
//        Assertions.assertEquals(actual.message, "The passwords entered by the user do not match.")
//
//        Assertions.assertFalse(registrationTokenRepository.findAll().isEmpty())
//        Assertions.assertTrue(accountRepository.findAll().isEmpty())
//    }
//
//    /**
//     * Регистрация аккаунта. Пароль недостаточно сложный.
//     */
//    @Test
//    fun `Weak password`() {
//        val token = UUID.randomUUID()
//        val password = DSL.generatePassword().substring(0, 3)
//
//        val registrationToken = RegistrationToken(token)
//        registrationTokenRepository.save(registrationToken)
//
//        val registrationDTO = RegistrationDTO(token.toString(), password, password)
//
//        val actual = RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(registrationDTO)
//            .`when`()
//            .baseUri(address())
//            .post(register())
//            .then()
//            .statusCode(400)
//            .extract()
//            .`as`(ExceptionDTO::class.java)
//
//        Assertions.assertEquals(
//            actual.message, "Password is too short. The password must be at least eight " +
//                    "characters long."
//        )
//
//        Assertions.assertFalse(registrationTokenRepository.findAll().isEmpty())
//        Assertions.assertTrue(accountRepository.findAll().isEmpty())
//    }
//
//    /**
//     * Регистрация аккаунта. Используем один и тот же токен дважды.
//     */
//    @Test
//    fun `Use token twice`() {
//        val token = UUID.randomUUID()
//        val password = DSL.generatePassword()
//
//        val registrationToken = RegistrationToken(token)
//        registrationTokenRepository.save(registrationToken)
//
//        val expected = AccessTokenDTO("", token.toString(), UserRole.ADMIN.value)
//
//        val registrationDTO = RegistrationDTO(token.toString(), password, password)
//
//        val actual = RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(registrationDTO)
//            .`when`()
//            .baseUri(address())
//            .post(register())
//            .then()
//            .statusCode(200)
//            .extract()
//            .`as`(AccessTokenDTO::class.java)
//
//        Assertions.assertNotNull(actual.accessToken)
//        Assertions.assertEquals(actual.userEntityRole, expected.userEntityRole)
//        Assertions.assertEquals(actual.userToken, expected.userToken)
//
//        val actual2 = RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(registrationDTO)
//            .`when`()
//            .baseUri(address())
//            .post(register())
//            .then()
//            .statusCode(400)
//            .extract()
//            .`as`(ExceptionDTO::class.java)
//
//        Assertions.assertEquals(actual2.message, "Registration token $token does not exist")
//
//        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty())
//        Assertions.assertEquals(1, accountRepository.findAll().size)
//
//        val account = accountRepository.findAll().stream().findFirst().get()
//        Assertions.assertEquals(account.login, token)
//        Assertions.assertFalse(account.password.isEmpty())
//        Assertions.assertEquals(account.role, UserEntityRole.ADMIN)
//    }
//}
