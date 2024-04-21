package ru.urfu.mm.application.usecase.create.student;

import ru.urfu.mm.application.gateway.*;
import ru.urfu.mm.application.usecase.create.DifferentPasswordException;
import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
import ru.urfu.mm.application.usecase.create.TooShortPasswordException;
import ru.urfu.mm.application.usecase.create.user.CreateUserRequest;
import ru.urfu.mm.domain.*;

/**
 * Создаем аккаунт студента
 * 1. Находим студента.
 * 2. Проверяем, что пароль надежный. Если не надежный, то кидаем ошибку о том, что пароль не надежен.
 * 3. Находим программу.
 * 4. Создаем аккаунт пользователя.
 * 5. Заполняем студента.
 * 6. Сохраняем изменения в студенте.
 */
public class CreateStudent {
    private final PasswordGateway passwordGateway;
    private final UserGateway userGateway;
    private final StudentGateway studentGateway;

    public CreateStudent(
            PasswordGateway passwordGateway,
            UserGateway userGateway,
            StudentGateway studentGateway) {
        this.passwordGateway = passwordGateway;
        this.userGateway = userGateway;
        this.studentGateway = studentGateway;
    }

    public void createStudent(CreateUserRequest request) {
        Student student = studentGateway.findById(request.token())
                .orElseThrow(() -> new RegistrationTokenNotExistException(request.token()));

        ensurePasswordsSame(request.password(), request.passwordAgain());
        ensurePasswordStrongEnough(request.password());

        Program program = student.getEducationalProgram();

        User user = new User(request.token(), passwordGateway.encode(request.password()), UserRole.STUDENT);
        userGateway.save(user);

        Student completedStudent = new Student(request.token(), program, student.getGroup(), user);
        studentGateway.update(completedStudent);
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
