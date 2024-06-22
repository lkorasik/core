package ru.urfu.mm.integration

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.urfu.mm.RestAssureExtension.whenever
import ru.urfu.mm.controller.Endpoints
import ru.urfu.mm.controller.course.CreateModuleCourseDTO
import ru.urfu.mm.dsl.AuthorizationDSL
import ru.urfu.mm.dsl.ModuleDSL
import ru.urfu.mm.persistance.entity.enums.Control
import ru.urfu.mm.persistance.repository.SpecialCourseRepository
import java.util.*

class `Course test` : BaseTestClass() {
    @Autowired
    private lateinit var authorization: AuthorizationDSL
    @Autowired
    private lateinit var moduleDSL: ModuleDSL
    @Autowired
    private lateinit var courseRepository: SpecialCourseRepository

    /**
     * Создание курса
     */
    @Test
    fun `create course`() {
        val bearer = authorization.registerAdministratorAccount(address())

        val moduleId = moduleDSL.createModule(address())

        val name = "Модуль_" + UUID.randomUUID()
        val credits = 3
        val controlType = Control.TEST
        val description = UUID.randomUUID().toString().replace("-", "")
        val department = UUID.randomUUID().toString().replace("-", "")
        val teacher = UUID.randomUUID().toString().replace("-", "")
        val createCourseDTO = CreateModuleCourseDTO(
            name,
            credits,
            controlType,
            description,
            moduleId,
            department,
            teacher
        )

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(createCourseDTO)
            .header("Authorization", "Bearer $bearer")
            .whenever()
            .baseUri(address())
            .post(Endpoints.Course.create())
            .then()
            .statusCode(200)

        val course = courseRepository.findAll().first { it.name.equals(name) }
        Assertions.assertTrue(course.name.equals(name))
        Assertions.assertTrue(course.creditsCount == credits)
        Assertions.assertTrue(course.control.equals(controlType))
        Assertions.assertTrue(course.description.equals(description))
        Assertions.assertTrue(course.department.equals(department))
        Assertions.assertTrue(course.teacherName.equals(teacher))
    }
}
