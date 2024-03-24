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
import ru.urfu.mm.applicationlegacy.gateway.LoggerGateway;
import ru.urfu.mm.applicationlegacy.gateway.PasswordGateway;
import ru.urfu.mm.applicationlegacy.gateway.TokenGateway;
import ru.urfu.mm.applicationlegacy.gateway.UserGateway;
import ru.urfu.mm.applicationlegacy.usecase.CreateAdministrator;
import ru.urfu.mm.domainlegacy.UserRole;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CreateAdministratorTest {
    @Mock
    private TokenGateway tokenGateway;
    @Mock
    private LoggerGateway loggerGateway;
    @Mock
    private PasswordGateway passwordGateway;
    @Mock
    private UserGateway userGateway;

    /**
     * Создание администратора.
     */
    @Test
    public void createAdministrator() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.ADMIN));

        CreateAdministrator createAdministrator = new CreateAdministrator(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway
        );

        createAdministrator.createAdministrator(token, password);
    }

    /**
     * Создание администратора. Регистрационный токен не внесен в базу данных.
     */
    @Test
    public void createAdministrator_noRegistrationToken() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.empty());

        CreateAdministrator createAdministrator = new CreateAdministrator(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway
        );

        Assertions.assertThrows(RegistrationTokenNotExistException.class, () -> createAdministrator.createAdministrator(token, password));
    }

    /**
     * Создание администратора. Неверно указан тип пользователя, который соответствует токену.
     */
    @Test
    public void createAdministrator_incorrectRole() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();

        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.STUDENT));

        CreateAdministrator createAdministrator = new CreateAdministrator(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway
        );

        Assertions.assertThrows(IncorrectUserRoleException.class, () -> createAdministrator.createAdministrator(token, password));
    }
}
