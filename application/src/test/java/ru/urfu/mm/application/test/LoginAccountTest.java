package ru.urfu.mm.application.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.DSL;
import ru.urfu.mm.application.usecase.login_user.InvalidCredentialsException;
import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.application.usecase.login_user.LoginRequest;
import ru.urfu.mm.application.usecase.login_user.LoginUser;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.domain.UserRole;

import java.util.Optional;
import java.util.UUID;

/**
 * Тестирование use case {@link LoginUser}
 */
@ExtendWith(MockitoExtension.class)
public class LoginAccountTest {
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

        Account account = new Account(token, password, UserRole.STUDENT);

        Mockito.when(userGateway.findByToken(token)).thenReturn(Optional.of(account));
        Mockito.when(passwordGateway.matches(password, account.password())).thenReturn(true);

        LoginUser loginUser = new LoginUser(userGateway, passwordGateway);

        LoginRequest loginRequest = new LoginRequest(token, password);
        Account gotAccount = loginUser.loginUser(loginRequest);

        Assertions.assertEquals(account, gotAccount);
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

        Account account = new Account(token, password, UserRole.STUDENT);

        Mockito.when(userGateway.findByToken(token)).thenReturn(Optional.of(account));
        Mockito.when(passwordGateway.matches(password, account.password())).thenReturn(false);

        LoginUser loginUser = new LoginUser(userGateway, passwordGateway);

        LoginRequest loginRequest = new LoginRequest(token, password);
        Assertions.assertThrows(InvalidCredentialsException.class, () -> loginUser.loginUser(loginRequest));
    }
}
