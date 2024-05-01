package ru.urfu.mm.integration.authorization;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

public class AccountRegistrationTest extends BaseTestClass {
    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;
    @Autowired
    private AccountRepository accountRepository;

    private final String API_REGISTRATION = "api/authentication/register";
    private final String API_LOGIN = "api/authentication/login";

    @AfterEach
    void clean() {
        registrationTokenRepository.deleteAll();
        accountRepository.deleteAll();
    }

    /**
     * Регистрация аккаунта. Пароли в регистрационной форме не совпадают.
     */
    @Test
    void registerAccount_notSamePasswords() {
        UUID token = UUID.randomUUID();
        String password = DSL.generatePassword();
        String passwordAgain = DSL.generatePassword();

        RegistrationToken registrationToken = new RegistrationToken(token);
        registrationTokenRepository.save(registrationToken);

        RegistrationDTO registrationDTO = new RegistrationDTO(String.valueOf(token), password, passwordAgain);

        ExceptionDTO actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .baseUri(address())
                .post(API_REGISTRATION)
                .then()
                .statusCode(400)
                .extract()
                .as(ExceptionDTO.class);

        Assertions.assertEquals(actual.message(), "The passwords entered by the user do not match.");

        Assertions.assertFalse(registrationTokenRepository.findAll().isEmpty());
        Assertions.assertTrue(accountRepository.findAll().isEmpty());
    }

    /**
     * Регистрация аккаунта. Пароль недостаточно сложный.
     */
    @Test
    void registerAccount_weakPassword() {
        UUID token = UUID.randomUUID();
        String password = DSL.generatePassword().substring(0, 3);

        RegistrationToken registrationToken = new RegistrationToken(token);
        registrationTokenRepository.save(registrationToken);

        RegistrationDTO registrationDTO = new RegistrationDTO(String.valueOf(token), password, password);

        ExceptionDTO actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .baseUri(address())
                .post(API_REGISTRATION)
                .then()
                .statusCode(400)
                .extract()
                .as(ExceptionDTO.class);

        Assertions.assertEquals(actual.message(), "Password is too short. The password must be at least eight " +
                "characters long.");

        Assertions.assertFalse(registrationTokenRepository.findAll().isEmpty());
        Assertions.assertTrue(accountRepository.findAll().isEmpty());
    }

    /**
     * Регистрация аккаунта. Используем один и тот же токен дважды.
     */
    @Test
    void registerAccount_useTokenTwice() {
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
                .post(API_REGISTRATION)
                .then()
                .statusCode(200)
                .extract()
                .as(AccessTokenDTO.class);

        Assertions.assertNotNull(actual.accessToken());
        Assertions.assertEquals(actual.userEntityRole(), expected.userEntityRole());
        Assertions.assertEquals(actual.userToken(), expected.userToken());

        ExceptionDTO actual2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registrationDTO)
                .when()
                .baseUri(address())
                .post(API_REGISTRATION)
                .then()
                .statusCode(400)
                .extract()
                .as(ExceptionDTO.class);

        Assertions.assertEquals(actual2.message(), "Registration token " + token + " does not exist");

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty());
        Assertions.assertEquals(1, accountRepository.findAll().size());

        AccountEntity account = accountRepository.findAll().stream().findFirst().get();
        Assertions.assertEquals(account.getLogin(), token);
        Assertions.assertFalse(account.getPassword().isEmpty());
        Assertions.assertEquals(account.getRole(), UserEntityRole.ADMIN);
    }
}
