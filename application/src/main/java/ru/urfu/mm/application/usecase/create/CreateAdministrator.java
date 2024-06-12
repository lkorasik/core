package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.application.usecase.create.account.CreateAccountRequest;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.domain.enums.UserRole;

/**
 * Зарегистрировать аккаунт администратора
 * 1. Создаем аккаунт пользователя.
 * 2. Сохраняем аккаунт пользователя.
 * 3. Удаляем токен из доступных токенов для регистрации.
 */
public class CreateAdministrator implements CreateUseCase {
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

    @Override
    public void create(CreateAccountRequest request) {
        String encodedPassword = passwordGateway.encode(request.getPassword());
        Account account = new Account(request.getToken(), encodedPassword, UserRole.ADMIN);
        userGateway.save(account);

        tokenGateway.deleteToken(request.getToken());
    }
}
