package ru.urfu.mm.integration

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.urfu.mm.controller.authentication.AccessTokenDTO
import ru.urfu.mm.controller.authentication.RegistrationDTO
import ru.urfu.mm.controller.program.CreateSemesterDTO
import ru.urfu.mm.domain.enums.UserRole
import ru.urfu.mm.dsl.AuthorizationDSL
import ru.urfu.mm.dsl.DSL
import ru.urfu.mm.persistance.entity.RegistrationToken
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository
import java.util.*

class EducationalProgramTest : BaseTestClass() {
    @Autowired
    private lateinit var repository: RegistrationTokenRepository
    @Autowired
    private lateinit var authorizationDSL: AuthorizationDSL

    /**
     * Создание программы
     */
    @Test
    fun createProgram() {
        val token = UUID.randomUUID()
        val password = DSL.generatePassword()

        val registrationToken = RegistrationToken(token)
        repository.save(registrationToken)

        val expected = AccessTokenDTO("", token.toString(), UserRole.ADMIN.value)

        val registrationDTO = RegistrationDTO(token.toString(), password, password)
        val actual = authorizationDSL.registerAsAdministratorAccount(registrationDTO, address())

        val createSemesterDTO1 = CreateSemesterDTO(listOf(), listOf(), listOf())
        val createSemesterDTO2 = CreateSemesterDTO(listOf(), listOf(), listOf())
        val createSemesterDTO3 = CreateSemesterDTO(listOf(), listOf(), listOf())
        val createSemesterDTO4 = CreateSemesterDTO(listOf(), listOf(), listOf())

        //        CreateProgramDTO createProgramDTO = new CreateProgramDTO(DSL.generateProgramName(), List.of(3, 3, 3, 3), List.of(createSemesterDTO1, createSemesterDTO2, createSemesterDTO3, createSemesterDTO4));
        RestAssured.given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + actual.accessToken) //                .body(createProgramDTO)
            .`when`()
            .baseUri(address())
            .post("/api/programs/create")
            .then()
            .statusCode(200)

        Assertions.assertNotNull(actual.accessToken)
        Assertions.assertEquals(actual.userEntityRole, expected.userEntityRole)
        Assertions.assertEquals(actual.userToken, expected.userToken)
    }
}
