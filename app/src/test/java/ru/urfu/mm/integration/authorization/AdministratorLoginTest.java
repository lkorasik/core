package ru.urfu.mm.integration.authorization;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.controller.ExceptionDTO;
import ru.urfu.mm.controller.authentication.AccessTokenDTO;
import ru.urfu.mm.controller.authentication.LoginDTO;
import ru.urfu.mm.controller.authentication.RegistrationDTO;
import ru.urfu.mm.domain.enums.UserRole;
import ru.urfu.mm.dsl.DSL;
import ru.urfu.mm.integration.BaseTestClass;
import ru.urfu.mm.persistance.entity.RegistrationToken;
import ru.urfu.mm.persistance.repository.AccountRepository;
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository;

import java.util.UUID;

public class AdministratorLoginTest extends BaseTestClass {
    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void clean() {
        registrationTokenRepository.deleteAll();
        accountRepository.deleteAll();
    }

    /**
     * Вход в систему администратором.
     */
    @Test
    void loginAdministrator() {
        UUID token = UUID.randomUUID();
        String password = DSL.generatePassword();

        RegistrationToken registrationToken = new RegistrationToken(token);
        registrationTokenRepository.save(registrationToken);

        AccessTokenDTO expected = new AccessTokenDTO("", token.toString(), UserRole.ADMIN.getValue());

        RegistrationDTO registrationDTO = new RegistrationDTO(String.valueOf(token), password, password);

        AccessTokenDTO actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .baseUri(address())
                .post(Endpoints.Authentication.BASE + Endpoints.Authentication.REGISTER)
                .then()
                .statusCode(200)
                .extract()
                .as(AccessTokenDTO.class);

        Assertions.assertNotNull(actual.accessToken());
        Assertions.assertEquals(actual.userEntityRole(), expected.userEntityRole());
        Assertions.assertEquals(actual.userToken(), expected.userToken());

        LoginDTO loginDTO = new LoginDTO(String.valueOf(token), password);

        AccessTokenDTO actual2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginDTO)
                .when()
                .baseUri(address())
                .post(Endpoints.Authentication.BASE + Endpoints.Authentication.LOGIN)
                .then()
                .statusCode(200)
                .extract()
                .as(AccessTokenDTO.class);

        Assertions.assertNotNull(actual2.accessToken());
        Assertions.assertEquals(actual2.userEntityRole(), expected.userEntityRole());
        Assertions.assertEquals(actual2.userToken(), expected.userToken());

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty());
        Assertions.assertEquals(1, accountRepository.findAll().size());
    }

    /**
     * Вход в систему администратором. Неверный токен.
     */
    @Test
    void loginAdministrator_tokenInvalid() {
        UUID token = UUID.randomUUID();
        String password = DSL.generatePassword();

        RegistrationToken registrationToken = new RegistrationToken(token);
        registrationTokenRepository.save(registrationToken);

        AccessTokenDTO expected = new AccessTokenDTO("", token.toString(), UserRole.ADMIN.getValue());

        RegistrationDTO registrationDTO = new RegistrationDTO(String.valueOf(token), password, password);

        AccessTokenDTO actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .baseUri(address())
                .post(Endpoints.Authentication.register())
                .then()
                .statusCode(200)
                .extract()
                .as(AccessTokenDTO.class);

        Assertions.assertNotNull(actual.accessToken());
        Assertions.assertEquals(actual.userEntityRole(), expected.userEntityRole());
        Assertions.assertEquals(actual.userToken(), expected.userToken());

        LoginDTO loginDTO = new LoginDTO(String.valueOf(UUID.randomUUID()), password);

        ExceptionDTO actual2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginDTO)
                .when()
                .baseUri(address())
                .post(Endpoints.Authentication.login())
                .then()
                .statusCode(400)
                .extract()
                .as(ExceptionDTO.class);

        Assertions.assertEquals("Invalid credentials. Please check your token and password.",
                actual2.message());

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty());
        Assertions.assertEquals(1, accountRepository.findAll().size());
    }

    /**
     * Вход в систему администратором. Неверный пароль.
     */
    @Test
    void loginAdministrator_incorrectPassword() {
        UUID token = UUID.randomUUID();
        String password = DSL.generatePassword();

        RegistrationToken registrationToken = new RegistrationToken(token);
        registrationTokenRepository.save(registrationToken);

        AccessTokenDTO expected = new AccessTokenDTO("", token.toString(), UserRole.ADMIN.getValue());

        RegistrationDTO registrationDTO = new RegistrationDTO(String.valueOf(token), password, password);

        AccessTokenDTO actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .baseUri(address())
                .post(Endpoints.Authentication.register())
                .then()
                .statusCode(200)
                .extract()
                .as(AccessTokenDTO.class);

        Assertions.assertNotNull(actual.accessToken());
        Assertions.assertEquals(actual.userEntityRole(), expected.userEntityRole());
        Assertions.assertEquals(actual.userToken(), expected.userToken());

        LoginDTO loginDTO = new LoginDTO(String.valueOf(token), String.valueOf(UUID.randomUUID()));

        ExceptionDTO actual2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginDTO)
                .when()
                .baseUri(address())
                .post(Endpoints.Authentication.login())
                .then()
                .statusCode(400)
                .extract()
                .as(ExceptionDTO.class);

        Assertions.assertEquals("Invalid credentials. Please check your token and password.", actual2.message());

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty());
        Assertions.assertEquals(1, accountRepository.findAll().size());
    }
}
