package ru.urfu.mm.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.urfu.mm.controller.ExceptionDTO;
import ru.urfu.mm.controller.authentication.AccessTokenDTO;
import ru.urfu.mm.controller.authentication.RegistrationDTO;
import ru.urfu.mm.domain.UserRole;
import ru.urfu.mm.dsl.DSL;
import ru.urfu.mm.persistance.entity.RegistrationToken;
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository;

import java.util.UUID;

public class AuthenticationTest extends AbstractTest {
    @Autowired
    RegistrationTokenRepository repository;

    /**
     * Тестирование регистрации администратора.
     */
    @Test
    void registerAdministrator() {
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

        Assertions.assertNotNull(actual.accessToken());
        Assertions.assertEquals(actual.userEntityRole(), expected.userEntityRole());
        Assertions.assertEquals(actual.userToken(), expected.userToken());
    }

    /**
     * Тестирование регистрации администратора. Токен не добавлен в базу данных.
     */
    @Test
    void registerAdministrator_noToken() {
        UUID token = UUID.randomUUID();
        String password = DSL.generatePassword();

        RegistrationDTO registrationDTO = new RegistrationDTO(String.valueOf(token), password, password);

        ExceptionDTO actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .baseUri(address())
                .post("/api/authentication/register")
                .then()
                .statusCode(400)
                .extract()
                .as(ExceptionDTO.class);

        Assertions.assertEquals(actual.message(), "Registration token " + token + " does not exist");
    }
}
