package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.DSL;
import ru.urfu.mm.application.usecase.createstudent.*;
import ru.urfu.mm.application.gateway.*;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.UserRole;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CreateStudentTest {
    @Mock
    private TokenGateway tokenGateway;
    @Mock
    private LoggerGateway loggerGateway;
    @Mock
    private PasswordGateway passwordGateway;
    @Mock
    private UserGateway userGateway;
    @Mock
    private ProgramGateway programGateway;
    @Mock
    private StudentGateway studentGateway;

    /**
     * Создание аккаунта студента.
     */
    @Test
    public void createStudent() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();
        UUID programId = UUID.randomUUID();
        String group = DSL.generateString();

        EducationalProgram program = new EducationalProgram();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.STUDENT));
        Mockito.when(programGateway.findById(programId)).thenReturn(Optional.of(program));

        CreateStudent createStudent = new CreateStudent(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        createStudent.createStudent(request);
    }

    /**
     * Создание аккаунта студента. Регистрационный токен не внесен в базу данных.
     */
    @Test
    public void createStudent_noRegistrationToken() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();
        UUID programId = UUID.randomUUID();
        String group = DSL.generateString();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.empty());

        CreateStudent createStudent = new CreateStudent(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        Assertions.assertThrows(RegistrationTokenNotExistException.class, () -> createStudent.createStudent(request));
    }

    /**
     * Создание аккаунта студента. Регистрационный токен имеет не соотвествующий тип аккаунта.
     */
    @Test
    public void createStudent_anotherAccountType() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();
        UUID programId = UUID.randomUUID();
        String group = DSL.generateString();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.ADMIN));

        CreateStudent createStudent = new CreateStudent(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        Assertions.assertThrows(IncorrectUserRoleException.class, () -> createStudent.createStudent(request));
    }

    /**
     * Создание аккаунта студента. Разные пароли.
     */
    @Test
    public void createStudent_differentPasswords() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();
        String passwordAgain = DSL.generateString();
        UUID programId = UUID.randomUUID();
        String group = DSL.generateString();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.STUDENT));

        CreateStudent createStudent = new CreateStudent(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, passwordAgain);
        Assertions.assertThrows(DifferentPasswordException.class, () -> createStudent.createStudent(request));
    }

    /**
     * Создание аккаунта студента. Пароль слишком короткий.
     */
    @Test
    public void createStudent_tooShortPassword() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString().substring(0, 5);
        UUID programId = UUID.randomUUID();
        String group = DSL.generateString();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.STUDENT));

        CreateStudent createStudent = new CreateStudent(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        Assertions.assertThrows(TooShortPasswordException.class, () -> createStudent.createStudent(request));
    }

    /**
     * Создание аккаунта студента. Образовательная программа не найдена.
     */
    @Test
    public void createStudent_programNotFound() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();
        UUID programId = UUID.randomUUID();
        String group = DSL.generateString();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.STUDENT));
        Mockito.when(programGateway.findById(programId)).thenReturn(Optional.empty());

        CreateStudent createStudent = new CreateStudent(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        Assertions.assertThrows(EducationalProgramNotExistsException.class, () -> createStudent.createStudent(request));
    }
}
