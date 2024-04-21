//package ru.urfu.mm.application.test;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.urfu.mm.application.dsl.DSL;
//import ru.urfu.mm.application.usecase.create.IncorrectUserRoleException;
//import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
//import ru.urfu.mm.application.gateway.LoggerGateway;
//import ru.urfu.mm.application.gateway.PasswordGateway;
//import ru.urfu.mm.application.gateway.TokenGateway;
//import ru.urfu.mm.application.gateway.UserGateway;
//import ru.urfu.mm.application.usecase.create.TooShortPasswordException;
//import ru.urfu.mm.application.usecase.create.administrator.CreateAdministrator;
//import ru.urfu.mm.application.usecase.create.user.CreateUserRequest;
//import ru.urfu.mm.domain.UserRole;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@ExtendWith(MockitoExtension.class)
//public class CreateAdministratorTest {
//    @Mock
//    private TokenGateway tokenGateway;
//    @Mock
//    private PasswordGateway passwordGateway;
//    @Mock
//    private UserGateway userGateway;
//
//    /**
//     * Создание администратора.
//     */
//    @Test
//    public void createAdministrator() {
//        UUID token = UUID.randomUUID();
//        String password = DSL.generateString();
//
//        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.ADMIN));
//
//        CreateAdministrator createAdministrator = new CreateAdministrator(
//                tokenGateway,
//                passwordGateway,
//                userGateway
//        );
//
//        CreateUserRequest request = new CreateUserRequest(token, password, password);
//        createAdministrator.createAdministrator(request);
//    }
//
//    /**
//     * Создание администратора. Регистрационный токен не внесен в базу данных.
//     */
//    @Test
//    public void createAdministrator_noRegistrationToken() {
//        UUID token = UUID.randomUUID();
//        String password = DSL.generateString();
//
//        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.empty());
//
//        CreateAdministrator createAdministrator = new CreateAdministrator(
//                tokenGateway,
//                passwordGateway,
//                userGateway
//        );
//
//        CreateUserRequest request = new CreateUserRequest(token, password, password);
//        Assertions.assertThrows(RegistrationTokenNotExistException.class,
//                () -> createAdministrator.createAdministrator(request));
//    }
//
//    /**
//     * Создание администратора. Неверно указан тип пользователя, который соответствует токену.
//     */
//    @Test
//    public void createAdministrator_incorrectRole() {
//        UUID token = UUID.randomUUID();
//        String password = DSL.generateString();
//
//        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.STUDENT));
//
//        CreateAdministrator createAdministrator = new CreateAdministrator(
//                tokenGateway,
//                passwordGateway,
//                userGateway
//        );
//
//        CreateUserRequest request = new CreateUserRequest(token, password, password);
//        Assertions.assertThrows(IncorrectUserRoleException.class,
//                () -> createAdministrator.createAdministrator(request));
//    }
//
//    /**
//     * Создание администратора. Разные пароли
//     */
//    @Test
//    public void createAdministrator_differentPasswords() {
//        UUID token = UUID.randomUUID();
//        String password = DSL.generateString();
//        String passwordAgain = DSL.generateString();
//
//        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.STUDENT));
//
//        CreateAdministrator createAdministrator = new CreateAdministrator(
//                tokenGateway,
//                passwordGateway,
//                userGateway
//        );
//
//        CreateUserRequest request = new CreateUserRequest(token, password, passwordAgain);
//        Assertions.assertThrows(IncorrectUserRoleException.class,
//                () -> createAdministrator.createAdministrator(request));
//    }
//
//    /**
//     * Создание администратора. Слабый пароль
//     */
//    @Test
//    public void createAdministrator_weakPassword() {
//        UUID token = UUID.randomUUID();
//        String password = DSL.generateString().substring(0, 5);
//
//        Mockito.when(tokenGateway.getRoleByToken(token)).thenReturn(Optional.of(UserRole.ADMIN));
//
//        CreateAdministrator createAdministrator = new CreateAdministrator(
//                tokenGateway,
//                passwordGateway,
//                userGateway
//        );
//
//        CreateUserRequest request = new CreateUserRequest(token, password, password);
//        Assertions.assertThrows(TooShortPasswordException.class,
//                () -> createAdministrator.createAdministrator(request));
//    }
//}
