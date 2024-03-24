package ru.urfu.mm.applicationlegacy.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.applicationlegacy.dsl.DSL;
import ru.urfu.mm.applicationlegacy.exception.IncorrectUserRoleException;
import ru.urfu.mm.applicationlegacy.exception.RegistrationTokenNotExistException;
import ru.urfu.mm.applicationlegacy.gateway.*;
import ru.urfu.mm.applicationlegacy.usecase.CreateStudent;
import ru.urfu.mm.domainlegacy.EducationalProgram;
import ru.urfu.mm.domainlegacy.UserRole;

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
     * Создание администратора.
     */
    @Test
    public void createStudent() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();
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

        createStudent.createStudent(token, password, programId, group);
    }

    /**
     * Создание администратора. Регистрационный токен не внесен в базу данных.
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

        Assertions.assertThrows(RegistrationTokenNotExistException.class, () -> createStudent.createStudent(token, password, programId, group));
    }

    /**
     * Создание администратора. Регистрационный токен не внесен в базу данных.
     */
    @Test
    public void createStudent_incorrectAccountType() {
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

        Assertions.assertThrows(IncorrectUserRoleException.class, () -> createStudent.createStudent(token, password, programId, group));
    }
}
