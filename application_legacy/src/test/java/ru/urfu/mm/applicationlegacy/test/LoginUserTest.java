package ru.urfu.mm.applicationlegacy.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.applicationlegacy.dsl.DSL;
import ru.urfu.mm.applicationlegacy.exception.BadCredentialsException;
import ru.urfu.mm.applicationlegacy.gateway.PasswordGateway;
import ru.urfu.mm.applicationlegacy.gateway.UserGateway;
import ru.urfu.mm.applicationlegacy.usecase.LoginUser;
import ru.urfu.mm.domainlegacy.User;
import ru.urfu.mm.domainlegacy.UserRole;

import java.util.UUID;

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
        String password = DSL.generateString();

        User user = new User(token, password, UserRole.STUDENT);

        Mockito.when(userGateway.getByToken(token)).thenReturn(user);
        Mockito.when(passwordGateway.matches(password, user.getPassword())).thenReturn(true);

        LoginUser loginUser = new LoginUser(
                userGateway,
                passwordGateway
        );

        loginUser.loginUser(token, password);
    }

    /**
     * Некорректный пароль
     */
    @Test
    public void login_incorrectPassword() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateString();

        User user = new User(token, password, UserRole.STUDENT);

        Mockito.when(userGateway.getByToken(token)).thenReturn(user);
        Mockito.when(passwordGateway.matches(password, user.getPassword())).thenReturn(false);

        LoginUser loginUser = new LoginUser(
                userGateway,
                passwordGateway
        );

        Assertions.assertThrows(BadCredentialsException.class, () -> loginUser.loginUser(token, password));
    }
}
