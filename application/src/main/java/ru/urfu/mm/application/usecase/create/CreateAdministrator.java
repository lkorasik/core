package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.application.usecase.create.user.CreateUserRequest;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.domain.UserRole;

/**
 * Зарегистрировать аккаунт администратора
 * 3. Создаем аккаунт пользователя.
 * 4. Удаляем токен из доступных токенов для регистрации.
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
    public void create(CreateUserRequest request) {
        User user = new User(request.token(), passwordGateway.encode(request.password()), UserRole.ADMIN);
        userGateway.save(user);

        tokenGateway.deleteToken(request.token());
    }
}
