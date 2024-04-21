package ru.urfu.mm.application.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.DSL;
import ru.urfu.mm.application.usecase.loginuser.InvalidCredentialsException;
import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.application.usecase.loginuser.LoginRequest;
import ru.urfu.mm.application.usecase.loginuser.LoginUser;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.domain.UserRole;

import java.util.Optional;
import java.util.UUID;

/**
 * Тестирование use case {@link LoginUser}
 */
@ExtendWith(MockitoExtension.class)
public class LoginUserTest {
    @Mock
    private PasswordGateway passwordGateway;
    @Mock
    private UserGateway userGateway;

    /**
     * Вход в систему
     */
    @Test
    public void login() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateStrongPassword();

        User user = new User(token, password, UserRole.STUDENT);

        Mockito.when(userGateway.findByToken(token)).thenReturn(Optional.of(user));
        Mockito.when(passwordGateway.matches(password, user.getPassword())).thenReturn(true);

        LoginUser loginUser = new LoginUser(userGateway, passwordGateway);

        LoginRequest loginRequest = new LoginRequest(token, password);
        User gotUser = loginUser.loginUser(loginRequest);

        Assertions.assertEquals(user, gotUser);
    }

    /**
     * Вход в систему с некорректным токеном
     */
    @Test
    public void incorrectToken() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateStrongPassword();

        Mockito.when(userGateway.findByToken(token)).thenReturn(Optional.empty());

        LoginUser loginUser = new LoginUser(userGateway, passwordGateway);

        LoginRequest loginRequest = new LoginRequest(token, password);
        Assertions.assertThrows(InvalidCredentialsException.class, () -> loginUser.loginUser(loginRequest));
    }

    /**
     * Вход в систему с некорректным паролем
     */
    @Test
    public void incorrectPassword() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateStrongPassword();

        User user = new User(token, password, UserRole.STUDENT);

        Mockito.when(userGateway.findByToken(token)).thenReturn(Optional.of(user));
        Mockito.when(passwordGateway.matches(password, user.getPassword())).thenReturn(false);

        LoginUser loginUser = new LoginUser(userGateway, passwordGateway);

        LoginRequest loginRequest = new LoginRequest(token, password);
        Assertions.assertThrows(InvalidCredentialsException.class, () -> loginUser.loginUser(loginRequest));
    }
}
