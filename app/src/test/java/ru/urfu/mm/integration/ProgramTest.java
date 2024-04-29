package ru.urfu.mm.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.urfu.mm.controller.authentication.AccessTokenDTO;
import ru.urfu.mm.controller.authentication.RegistrationDTO;
import ru.urfu.mm.controller.program.CreateProgramDTO;
import ru.urfu.mm.controller.program.CreateSemesterDTO;
import ru.urfu.mm.domain.enums.UserRole;
import ru.urfu.mm.dsl.DSL;
import ru.urfu.mm.persistance.entity.RegistrationToken;
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository;

import java.util.List;
import java.util.UUID;

public class ProgramTest extends AbstractTest {
    @Autowired
    private RegistrationTokenRepository repository;

    /**
     * Создание программы
     */
    @Test
    void createProgram() {
        UUID token = UUID.randomUUID();
        String password = DSL.generatePassword();

        RegistrationToken registrationToken = new RegistrationToken(token);
        repository.save(registrationToken);

        AccessTokenDTO expected = new AccessTokenDTO("", token.toString(), UserRole.ADMIN.getValue());

        RegistrationDTO registrationDTO = new RegistrationDTO(String.valueOf(token), password, password);

        AccessTokenDTO actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .baseUri(address())
                .post("/api/authentication/register")
                .then()
                .statusCode(200)
                .extract()
                .as(AccessTokenDTO.class);

        CreateSemesterDTO createSemesterDTO1 = new CreateSemesterDTO(List.of(), List.of(), List.of());
        CreateSemesterDTO createSemesterDTO2 = new CreateSemesterDTO(List.of(), List.of(), List.of());
        CreateSemesterDTO createSemesterDTO3 = new CreateSemesterDTO(List.of(), List.of(), List.of());
        CreateSemesterDTO createSemesterDTO4 = new CreateSemesterDTO(List.of(), List.of(), List.of());
        CreateProgramDTO createProgramDTO = new CreateProgramDTO(DSL.generateProgramName(), List.of(3, 3, 3, 3), List.of(createSemesterDTO1, createSemesterDTO2, createSemesterDTO3, createSemesterDTO4));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + actual.accessToken())
                .body(createProgramDTO)
                .when()
                .baseUri(address())
                .post("/api/programs/create")
                .then()
                .statusCode(200);

        Assertions.assertNotNull(actual.accessToken());
        Assertions.assertEquals(actual.userEntityRole(), expected.userEntityRole());
        Assertions.assertEquals(actual.userToken(), expected.userToken());
    }
}
