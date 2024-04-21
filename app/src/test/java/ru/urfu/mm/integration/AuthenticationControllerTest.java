package ru.urfu.mm.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.urfu.mm.controller.authentication.AccessTokenDTO;
import ru.urfu.mm.controller.authentication.RegistrationDTO;
import ru.urfu.mm.domain.UserRole;
import ru.urfu.mm.entity.RegistrationToken;
import ru.urfu.mm.repository.RegistrationTokenRepository;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    RegistrationTokenRepository repository;

    /**
     * Тестирование регистрации администратора.
     */
    @Test
    void registerAdministrator() {
        UUID token = UUID.randomUUID();
        String password = UUID.randomUUID().toString();

        RegistrationToken registrationToken = new RegistrationToken(token);
        repository.save(registrationToken);

        AccessTokenDTO expected = new AccessTokenDTO("", token.toString(), UserRole.ADMIN.getValue());

        RegistrationDTO registrationDTO = new RegistrationDTO(String.valueOf(token), password, password);

        AccessTokenDTO actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .baseUri("http://localhost:" + port)
                .post("/api/authentication/register")
                .then()
                .statusCode(200)
                .extract()
                .as(AccessTokenDTO.class);

        Assertions.assertNotNull(actual.accessToken());
        Assertions.assertEquals(actual.userEntityRole(), expected.userEntityRole());
        Assertions.assertEquals(actual.userToken(), expected.userToken());
    }
}
