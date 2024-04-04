package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.exception.IncorrectUserRoleException;
import ru.urfu.mm.applicationlegacy.exception.RegistrationTokenNotExistException;
import ru.urfu.mm.applicationlegacy.gateway.LoggerGateway;
import ru.urfu.mm.applicationlegacy.gateway.PasswordGateway;
import ru.urfu.mm.applicationlegacy.gateway.TokenGateway;
import ru.urfu.mm.applicationlegacy.gateway.UserGateway;
import ru.urfu.mm.domainlegacy.User;
import ru.urfu.mm.domainlegacy.UserRole;

import java.util.UUID;

public class CreateAdministrator {
    private final TokenGateway tokenGateway;
    private final LoggerGateway loggerGateway;
    private final PasswordGateway passwordGateway;
    private final UserGateway userGateway;

    public CreateAdministrator(
            TokenGateway tokenGateway,
            LoggerGateway loggerGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway) {
        this.tokenGateway = tokenGateway;
        this.loggerGateway = loggerGateway;
        this.passwordGateway = passwordGateway;
        this.userGateway = userGateway;
    }

    public void createAdministrator(UUID token, String password) {
        UserRole role = tokenGateway
                .getRoleByToken(token)
                .orElseThrow(() -> new RegistrationTokenNotExistException(token));
        ensureRole(role, token.toString());

        User user = new User(token, passwordGateway.encode(password), role);
        userGateway.save(user);

        tokenGateway.deleteToken(token);
    }

    private void ensureRole(UserRole current, String token) {
        if(current != UserRole.ADMIN) {
            if(current != null) {
                loggerGateway.warn("Registration token " + token + " was used for " + UserRole.ADMIN + " registration");
            }
            throw new IncorrectUserRoleException(token);
        }
    }
}
