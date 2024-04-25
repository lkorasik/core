package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.DSL;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.usecase.create.*;
import ru.urfu.mm.application.usecase.create.account.CreateAccount;
import ru.urfu.mm.application.usecase.create.account.CreateUserRequest;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CreateAccountTest {
    @Mock
    private CreateStudent createStudent;
    @Mock
    private CreateAdministrator createAdministrator;
    @Mock
    private TokenGateway tokenGateway;

    /**
     * Создание администратора
     */
    @Test
    public void createAdministrator() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateStrongPassword();

        Mockito.when(tokenGateway.isAdministratorToken(token)).thenReturn(true);

        CreateAccount createAccount = new CreateAccount(createStudent, createAdministrator, tokenGateway);

        CreateUserRequest request = new CreateUserRequest(token, password, password);
        createAccount.createUser(request);
    }

    /**
     * Создание студента
     */
    @Test
    public void createStudent() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateStrongPassword();

        Mockito.when(tokenGateway.isAdministratorToken(token)).thenReturn(false);
        Mockito.when(tokenGateway.isStudentToken(token)).thenReturn(true);

        CreateAccount createAccount = new CreateAccount(createStudent, createAdministrator, tokenGateway);

        CreateUserRequest request = new CreateUserRequest(token, password, password);
        createAccount.createUser(request);
    }

    /**
     * Создание пользователя. Токен не является токеном студента или администратора.
     */
    @Test
    public void invalidToken() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateStrongPassword();

        Mockito.when(tokenGateway.isAdministratorToken(token)).thenReturn(false);
        Mockito.when(tokenGateway.isStudentToken(token)).thenReturn(false);

        CreateAccount createAccount = new CreateAccount(createStudent, createAdministrator, tokenGateway);

        CreateUserRequest request = new CreateUserRequest(token, password, password);
        Assertions.assertThrows(RegistrationTokenNotExistException.class, () -> createAccount.createUser(request));
    }

    /**
     * Создание студента. Разные пароли.
     */
    @Test
    public void differentPasswords() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateStrongPassword();
        String passwordAgain = DSL.generateStrongPassword();

        Mockito.when(tokenGateway.isAdministratorToken(token)).thenReturn(false);
        Mockito.when(tokenGateway.isStudentToken(token)).thenReturn(true);

        CreateAccount createAccount = new CreateAccount(createStudent, createAdministrator, tokenGateway);

        CreateUserRequest request = new CreateUserRequest(token, password, passwordAgain);
        Assertions.assertThrows(DifferentPasswordException.class, () -> createAccount.createUser(request));
    }

    /**
     * Создание студента. Пароль слишком слабый.
     */
    @Test
    public void weakPassword() {
        UUID token = UUID.randomUUID();
        String password = DSL.generateWeakPassword();

        Mockito.when(tokenGateway.isAdministratorToken(token)).thenReturn(false);
        Mockito.when(tokenGateway.isStudentToken(token)).thenReturn(true);

        CreateAccount createAccount = new CreateAccount(createStudent, createAdministrator, tokenGateway);

        CreateUserRequest request = new CreateUserRequest(token, password, password);
        Assertions.assertThrows(TooShortPasswordException.class, () -> createAccount.createUser(request));
    }
}
