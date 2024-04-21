package ru.urfu.mm.application.usecase.create.user;

import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
import ru.urfu.mm.application.usecase.create.administrator.CreateAdministrator;
import ru.urfu.mm.application.usecase.create.student.CreateStudent;
import ru.urfu.mm.domain.UserRole;

/**
 * Создать аккаунт
 * 1. Находим токен. Если токен не найден либо он занят, то кидаем ошибку.
 * 2. В зависимости от типа токена делегируем задачу создания аккаунта либо
 * {@link ru.urfu.mm.application.usecase.create.student.CreateStudent}, либо
 * {@link ru.urfu.mm.application.usecase.create.administrator.CreateAdministrator}
 */
public class CreateUser {
    private final CreateStudent createStudent;
    private final CreateAdministrator createAdministrator;
    private final TokenGateway tokenGateway;

    public CreateUser(CreateStudent createStudent, CreateAdministrator createAdministrator, TokenGateway tokenGateway) {
        this.createStudent = createStudent;
        this.createAdministrator = createAdministrator;
        this.tokenGateway = tokenGateway;
    }

    public UserRole createUser(CreateUserRequest request) {
        if (tokenGateway.isAdministratorToken(request.token())) {
            createAdministrator.createAdministrator(request);
            return UserRole.ADMIN;
        } else if (tokenGateway.isStudentToken(request.token())) {
            createStudent.createStudent(request);
            return UserRole.STUDENT;
        } else {
            throw new RegistrationTokenNotExistException(request.token());
        }
    }
}
