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
import ru.urfu.mm.controller.authentication.RegistrationDTO;
import ru.urfu.mm.domain.enums.UserRole;
import ru.urfu.mm.dsl.DSL;
import ru.urfu.mm.integration.BaseTestClass;
import ru.urfu.mm.persistance.entity.AccountEntity;
import ru.urfu.mm.persistance.entity.RegistrationToken;
import ru.urfu.mm.persistance.entity.enums.UserEntityRole;
import ru.urfu.mm.persistance.repository.AccountRepository;
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository;

import java.util.UUID;

public class AdministratorRegistrationTest extends BaseTestClass {
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
     * Регистрация администратора
     */
    @Test
    void registerAdministrator() {
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

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty());
        Assertions.assertEquals(1, accountRepository.findAll().size());

        AccountEntity account = accountRepository.findAll().stream().findFirst().get();
        Assertions.assertEquals(account.getLogin(), token);
        Assertions.assertFalse(account.getPassword().isEmpty());
        Assertions.assertEquals(account.getRole(), UserEntityRole.ADMIN);
    }

    /**
     * Регистрация администратора. Токен не добавлен в базу данных.
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
                .post(Endpoints.Authentication.register())
                .then()
                .statusCode(400)
                .extract()
                .as(ExceptionDTO.class);

        Assertions.assertEquals(actual.message(), "Registration token " + token + " does not exist");

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty());
        Assertions.assertTrue(accountRepository.findAll().isEmpty());
    }
}
