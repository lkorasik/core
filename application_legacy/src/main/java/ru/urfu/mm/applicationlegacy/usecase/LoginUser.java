package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.exception.BadCredentialsException;
import ru.urfu.mm.applicationlegacy.gateway.PasswordGateway;
import ru.urfu.mm.applicationlegacy.gateway.UserGateway;
import ru.urfu.mm.domainlegacy.User;

import java.util.UUID;

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
