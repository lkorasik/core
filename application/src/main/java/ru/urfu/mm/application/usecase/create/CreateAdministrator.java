package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.application.usecase.create.DifferentPasswordException;
import ru.urfu.mm.application.usecase.create.TooShortPasswordException;
import ru.urfu.mm.application.usecase.create.user.CreateUserRequest;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.domain.UserRole;

/**
 * Зарегистрировать аккаунт студента1
 * 1. Проверяем, что пароль и его повтор совпадают. Если они отличатся, то кидаем ошибку.
 * 2. Проверяем, что пароль надежный. Если не надежный, то кидаем ошибку о том, что пароль не надежен.
 * 4. Создаем аккаунт пользователя.
 * 7. Отмечаем токен как использованный.
 */
public class CreateAdministrator {
    private final TokenGateway tokenGateway;
    private final PasswordGateway passwordGateway;
    private final UserGateway userGateway;

    public CreateAdministrator(
            TokenGateway tokenGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway) {
        this.tokenGateway = tokenGateway;
        this.passwordGateway = passwordGateway;
        this.userGateway = userGateway;
    }

    public void createAdministrator(CreateUserRequest request) {
        ensurePasswordsSame(request.password(), request.passwordAgain());
        ensurePasswordStrongEnough(request.password());

        User user = new User(request.token(), passwordGateway.encode(request.password()), UserRole.ADMIN);
        userGateway.save(user);

        tokenGateway.deleteToken(request.token());
    }

    private void ensurePasswordsSame(String password, String passwordAgain) {
        if (!password.equals(passwordAgain)) {
            throw new DifferentPasswordException();
        }
    }

    private void ensurePasswordStrongEnough(String password) {
        if (password.length() < 8) {
            throw new TooShortPasswordException();
        }
    }
}
