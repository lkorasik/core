package ru.urfu.mm.integration

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.urfu.mm.RestAssureExtension.whenever
import ru.urfu.mm.controller.Endpoints
import ru.urfu.mm.controller.modules.CreateModuleDTO
import ru.urfu.mm.dsl.AuthorizationDSL
import ru.urfu.mm.persistance.repository.EducationalModuleRepository
import java.util.*

class `Educational module test` : BaseTestClass() {
    @Autowired
    private lateinit var authorization: AuthorizationDSL
    @Autowired
    private lateinit var moduleRepository: EducationalModuleRepository

    /**
     * Создание модуля
     */
    @Test
    fun `create module`() {
        val bearer = authorization.registerAdministratorAccount(address())

        val name = "Модуль_" + UUID.randomUUID()
        val createProgramDTO = CreateModuleDTO(name)

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(createProgramDTO)
            .header("Authorization", "Bearer $bearer")
            .whenever()
            .baseUri(address())
            .post(Endpoints.Module.create())
            .then()
            .statusCode(200)

        val program = moduleRepository.findAll().first { it.name.equals(name) }
        Assertions.assertTrue(program.name.equals(name))
    }
}
