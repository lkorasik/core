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
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.entity.StudentEntity;
import ru.urfu.mm.persistance.entity.enums.UserEntityRole;
import ru.urfu.mm.persistance.entity.enums.Years;
import ru.urfu.mm.persistance.repository.AccountRepository;
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.RegistrationTokenRepository;
import ru.urfu.mm.persistance.repository.StudentRepository;

import java.util.UUID;

public class StudentRegistrationTest extends BaseTestClass {
    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private AccountRepository accountRepository;

    private final String API_REGISTRATION = "api/authentication/register";
    private final String API_LOGIN = "api/authentication/login";

    @AfterEach
    void clean() {
        registrationTokenRepository.deleteAll();
        studentRepository.deleteAll();
        groupRepository.deleteAll();
        accountRepository.deleteAll();
    }

    /**
     * Регистрация студента.
     */
    @Test
    void registerStudent() {
        UUID token = UUID.randomUUID();
        String password = DSL.generatePassword();

        GroupEntity group = new GroupEntity(UUID.randomUUID(), "МЕНМ-123123", Years.FIRST);
        groupRepository.save(group);
        StudentEntity student = new StudentEntity(token, group);
        studentRepository.save(student);

        AccessTokenDTO expected = new AccessTokenDTO("", token.toString(), UserRole.STUDENT.getValue());

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

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty());
        Assertions.assertEquals(1, accountRepository.findAll().size());

        AccountEntity account = accountRepository.findAll().stream().findFirst().get();
        Assertions.assertEquals(account.getLogin(), token);
        Assertions.assertFalse(account.getPassword().isEmpty());
        Assertions.assertEquals(account.getRole(), UserEntityRole.STUDENT);

        StudentEntity studentEntity = studentRepository.findAll().stream().findFirst().get();
        Assertions.assertEquals(studentEntity.getId(), token);
        Assertions.assertEquals(studentEntity.getGroup().getId(), group.getId());
        Assertions.assertEquals(studentEntity.getGroup().getNumber(), group.getNumber());
        Assertions.assertEquals(studentEntity.getGroup().getYear(), group.getYear());
        Assertions.assertEquals(studentEntity.getUser().getLogin(), account.getLogin());
        Assertions.assertEquals(studentEntity.getUser().getRole(), account.getRole());
    }

    /**
     * Регистрация студента. Студент не найден.
     */
    @Test
    void registerStudent_studentNotFound() {
        UUID token = UUID.randomUUID();
        String password = DSL.generatePassword();

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

        Assertions.assertEquals(actual.message(), "Registration token " + token + " does not exist");

        Assertions.assertTrue(registrationTokenRepository.findAll().isEmpty());

        Assertions.assertTrue(studentRepository.findAll().isEmpty());
        Assertions.assertTrue(accountRepository.findAll().isEmpty());
    }
}
