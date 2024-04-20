package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.DSL;
import ru.urfu.mm.application.usecase.create.DifferentPasswordException;
import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
import ru.urfu.mm.application.usecase.create.TooShortPasswordException;
import ru.urfu.mm.application.usecase.create.student.*;
import ru.urfu.mm.application.gateway.*;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.UserRole;
import ru.urfu.mm.application.usecase.create.IncorrectUserRoleException;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CreateStudentImplTest {
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

        Program program = new Program();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.STUDENT));
        Mockito.when(programGateway.findById(programId)).thenReturn(Optional.of(program));

        CreateStudentImpl createStudentImpl = new CreateStudentImpl(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        createStudentImpl.createStudent(request);
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

        CreateStudentImpl createStudentImpl = new CreateStudentImpl(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        Assertions.assertThrows(RegistrationTokenNotExistException.class, () -> createStudentImpl.createStudent(request));
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

        CreateStudentImpl createStudentImpl = new CreateStudentImpl(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        Assertions.assertThrows(IncorrectUserRoleException.class, () -> createStudentImpl.createStudent(request));
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

        CreateStudentImpl createStudentImpl = new CreateStudentImpl(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, passwordAgain);
        Assertions.assertThrows(DifferentPasswordException.class, () -> createStudentImpl.createStudent(request));
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

        CreateStudentImpl createStudentImpl = new CreateStudentImpl(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        Assertions.assertThrows(TooShortPasswordException.class, () -> createStudentImpl.createStudent(request));
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

        CreateStudentImpl createStudentImpl = new CreateStudentImpl(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway
        );

        CreateStudentRequest request = new CreateStudentRequest(token, programId, group, password, password);
        Assertions.assertThrows(EducationalProgramNotExistsException.class, () -> createStudentImpl.createStudent(request));
    }
}
