package ru.urfu.mm.integration

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.urfu.mm.RestAssureExtension.whenever
import ru.urfu.mm.controller.Endpoints
import ru.urfu.mm.controller.program.CreateProgramDTO
import ru.urfu.mm.dsl.AuthorizationDSL
import ru.urfu.mm.persistance.repository.ProgramRepository
import java.util.*

class EducationalProgramTest : BaseTestClass() {
    @Autowired
    private lateinit var authorization: AuthorizationDSL
    @Autowired
    private lateinit var programRepository: ProgramRepository

    /**
     * Создание программы
     */
    @Test
    fun createProgram() {
        val bearer = authorization.registerAdministratorAccount(address())

        val name = "Программа_" + UUID.randomUUID()
        val trainingDirection = "Направление_" + UUID.randomUUID()
        val createProgramDTO = CreateProgramDTO(name, trainingDirection)

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(createProgramDTO)
            .header("Authorization", "Bearer $bearer")
            .whenever()
            .baseUri(address())
            .post(Endpoints.Program.create())
            .then()
            .statusCode(200)

        val program = programRepository.findAll().first { it.name.equals(name) }
        Assertions.assertTrue(program.name.equals(name))
        Assertions.assertTrue(program.trainingDirection.equals(trainingDirection))
    }
}
