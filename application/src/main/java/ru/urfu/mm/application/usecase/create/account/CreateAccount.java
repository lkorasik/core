package ru.urfu.mm.application.usecase.create.account;

import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.usecase.create.*;
import ru.urfu.mm.domain.enums.UserRole;
import ru.urfu.mm.domain.exception.NotImplementedException;

/**
 * Создать аккаунт
 * 1. Находим токен. Если токен не найден или он уже занят, то кидаем ошибку о том, что такого токена для регистрации
 * не существует.
 * 2. Проверяем, что пароль и его повтор совпадают. Если они отличатся, то кидаем ошибку о том, что пароль и его повтор
 * не совпадают.
 * 3. Проверяем, что пароль надежный. Если не надежный, то кидаем ошибку о том, что пароль не надежен.
 * 4. В зависимости от типа токена делегируем задачу создания аккаунта либо {@link CreateStudent}, либо
 * {@link CreateAdministrator}.
 */
public class CreateAccount {
//    private final CreateStudent createStudent;
    private final CreateAdministrator createAdministrator;
    private final TokenGateway tokenGateway;

    public CreateAccount(
//            CreateStudent createStudent,
            CreateAdministrator createAdministrator, TokenGateway tokenGateway) {
//        this.createStudent = createStudent;
        this.createAdministrator = createAdministrator;
        this.tokenGateway = tokenGateway;
    }

    public UserRole createUser(CreateAccountRequest request) {
        UserRole role;
        CreateUseCase createUseCase;
        if (tokenGateway.isAdministratorToken(request.token())) {
            createUseCase = createAdministrator;
            role = UserRole.ADMIN;
        } else if (tokenGateway.isStudentToken(request.token())) {
//            createUseCase = createStudent;
            role = UserRole.STUDENT;

            throw new NotImplementedException();
        } else {
            throw new RegistrationTokenNotExistException(request.token());
        }

        ensurePasswordsSame(request.password(), request.passwordAgain());
        ensurePasswordStrongEnough(request.password());

        createUseCase.create(request);
        return role;
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
