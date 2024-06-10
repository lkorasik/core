package ru.urfu.mm.application.usecase.login_user;

import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.domain.Account;

/**
 * Вход в систему
 * 1. Проверяем существование пользователя. Если такого пользователя нет, то кидаем ошибку о том, что предоставлены
 * неверные данные.
 * 2. Проверяем пароль. Если пароль не совпадает с указанным в базе данных, то кидаем ошибку о том, что предоставлены
 * неверные данные.
 * 3. Возвращаем аккаунт пользователя.
 */
public class LoginUser {
    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;

    public LoginUser(UserGateway userGateway, PasswordGateway passwordGateway) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
    }

    public Account loginUser(LoginRequest loginRequest) {
        Account account = userGateway
                .findByToken(loginRequest.getToken())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordGateway.matches(loginRequest.getPassword(), account.password())) {
            throw new InvalidCredentialsException();
        }

        return account;
    }
}
