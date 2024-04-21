package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.DSL;
import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.application.usecase.create.CreateAdministrator;
import ru.urfu.mm.application.usecase.create.user.CreateUserRequest;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CreateAdministratorTest {
    @Mock
    private TokenGateway tokenGateway;
    @Mock
    private PasswordGateway passwordGateway;
    @Mock
    private UserGateway userGateway;

    /**
     * Создание администратора.
     */
    @Test
    public void createAdministrator() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateStrongPassword();

        CreateAdministrator createAdministrator = new CreateAdministrator(tokenGateway, passwordGateway, userGateway);

        CreateUserRequest request = new CreateUserRequest(token, password, password);
        createAdministrator.create(request);
    }
}
