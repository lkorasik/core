package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.exception.BadCredentialsException;
import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.domain.User;

import java.util.UUID;

/**
 * Вход в систему
 */
public class LoginUser {
    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;

    public LoginUser(UserGateway userGateway, PasswordGateway passwordGateway) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
    }

    public User loginUser(UUID token, String password) {
        User user = userGateway.getByToken(token);

        if (!passwordGateway.matches(password, user.getPassword())) {
            throw new BadCredentialsException();
        }

        return user;
    }
}
