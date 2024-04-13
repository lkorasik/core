package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.usecase.createstudent.IncorrectUserRoleException;
import ru.urfu.mm.application.usecase.createstudent.RegistrationTokenNotExistException;
import ru.urfu.mm.application.gateway.LoggerGateway;
import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.domain.UserRole;

import java.util.UUID;

/**
 * Зарегистрировать аккаунт студента
 * 1. Проверяем, что токен не занят. Если заняет, то кидаем ошибку с информацией о том, что токен уже использовался для
 * создания аккаунта.
 * 2. Проверяем, что токен связан с ролью администратора. Если не связан с ролью, то кидаем ошибку о том, что токен
 * не является токеном администратора.
 * 3. Проверяем, что пароль надежный. Если не надежный, то кидаем ошибку о том, что пароль не надежен.
 * 4. Создаем аккаунт пользователя.
 * 7. Отмечаем токен как использованный.
 */
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
        ensureRole(role, token);

        User user = new User(token, passwordGateway.encode(password), role);
        userGateway.save(user);

        tokenGateway.deleteToken(token);
    }

    private void ensureRole(UserRole current, UUID token) {
        if(current != UserRole.ADMIN) {
            if(current != null) {
                loggerGateway.warn("Registration token " + token + " was used for " + UserRole.ADMIN + " registration");
            }
            throw new IncorrectUserRoleException(token);
        }
    }
}
