package ru.urfu.mm.application.usecase.loginuser;

import ru.urfu.mm.application.exception.BadCredentialsException;
import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.domain.User;

/**
 * Вход в систему
 * 1. Проверяем, что такой пользователь вообще существует. Если такого пользователя нет, то кидаем ошибку с информаицей
 * о том, что такого аккаунта не существует.
 * 2. Проверяем пароль. Если пароль не совпадает с указанным в базе данных, то кидаем ошибку о некорректном пароле.
 * 3. Возвращаем аккаунт пользователя.
 */
public class LoginUser {
    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;

    public LoginUser(UserGateway userGateway, PasswordGateway passwordGateway) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
    }

    public User loginUser(LoginRequest loginRequest) {
        User user = userGateway.getByToken(loginRequest.token());

        if (!passwordGateway.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException();
        }

        return user;
    }
}
