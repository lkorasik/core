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
//import ru.urfu.mm.persistance.entity.GroupEntity
//import ru.urfu.mm.persistance.entity.StudentEntity
//import ru.urfu.mm.persistance.entity.enums.UserEntityRole
//import ru.urfu.mm.persistance.entity.enums.Years
//import ru.urfu.mm.persistance.repository.AccountRepository
//import ru.urfu.mm.persistance.repository.GroupRepository
//import ru.urfu.mm.persistance.repository.RegistrationTokenRepository
//import ru.urfu.mm.persistance.repository.StudentRepository
//import java.util.*
//
//class `Student registration` : BaseTestClass() {
//    @Autowired
//    private lateinit var registrationTokenRepository: RegistrationTokenRepository
//
//    @Autowired
//    private lateinit var groupRepository: GroupRepository
//
//    @Autowired
//    private lateinit var studentRepository: StudentRepository
//
//    @Autowired
//    private lateinit var accountRepository: AccountRepository
//
//    @AfterEach
//    fun clean() {
//        registrationTokenRepository.deleteAll()
//        studentRepository.deleteAll()
//        groupRepository.deleteAll()
//        accountRepository.deleteAll()
//    }
//
//    /**
//     * Регистрация студента.
//     */
//    @Test
//    fun `Register student`() {
//        val token = UUID.randomUUID()
//        val password = DSL.generatePassword()
//
//        val group = GroupEntity(UUID.randomUUID(), "МЕНМ-123123", Years.FIRST)
//        groupRepository.save(group)
//        val student = StudentEntity(token, group)
//        studentRepository.save(student)
//
//        val expected = AccessTokenDTO("", token.toString(), UserRole.STUDENT.value)
//
//        val registrationDTO = RegistrationDTO(token.toString(), password, password)
//
//        val actual = RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(registrationDTO)
//            .whenever()
//            .baseUri(address())
//            .post(register())
//            .then()
//            .statusCode(200)
//            .extract()
//            .cast(AccessTokenDTO::class.java)
//
//        Assertions.assertNotNull(actual.accessToken)
//        Assertions.assertEquals(actual.userEntityRole, expected.userEntityRole)
//        Assertions.assertEquals(actual.userToken, expected.userToken)
//
//        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty())
//        Assertions.assertEquals(1, accountRepository.findAll().size)
//
//        val account = accountRepository.findAll().stream().findFirst().get()
//        Assertions.assertEquals(account.login, token)
//        Assertions.assertFalse(account.password.isEmpty())
//        Assertions.assertEquals(account.role, UserEntityRole.STUDENT)
//
//        val studentEntity = studentRepository.findAll().stream().findFirst().get()
//        Assertions.assertEquals(studentEntity.id, token)
//        Assertions.assertEquals(studentEntity.group.id, group.id)
//        Assertions.assertEquals(studentEntity.group.number, group.number)
//        Assertions.assertEquals(studentEntity.group.year, group.year)
//        Assertions.assertEquals(studentEntity.user.login, account.login)
//        Assertions.assertEquals(studentEntity.user.role, account.role)
//    }
//
//    /**
//     * Регистрация студента. Студент не найден.
//     */
//    @Test
//    fun `Student not found`() {
//        val token = UUID.randomUUID()
//        val password = DSL.generatePassword()
//
//        val registrationDTO = RegistrationDTO(token.toString(), password, password)
//
//        val actual = RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(registrationDTO)
//            .whenever()
//            .baseUri(address())
//            .post(register())
//            .then()
//            .statusCode(400)
//            .extract()
//            .cast(ExceptionDTO::class.java)
//
//        Assertions.assertEquals(actual.message, "Registration token $token does not exist")
//
//        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty())
//
//        Assertions.assertTrue(studentRepository.findAll().isEmpty())
//        Assertions.assertTrue(accountRepository.findAll().isEmpty())
//    }
//}
