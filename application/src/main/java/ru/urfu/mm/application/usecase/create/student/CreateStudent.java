package ru.urfu.mm.application.usecase.create.student;

import ru.urfu.mm.application.gateway.*;
import ru.urfu.mm.application.usecase.create.DifferentPasswordException;
import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
import ru.urfu.mm.application.usecase.create.TooShortPasswordException;
import ru.urfu.mm.application.usecase.create.IncorrectUserRoleException;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Student;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.domain.UserRole;

import java.util.UUID;

/**
 * Зарегистрировать аккаунт студента
 * 1. Проверяем, что токен не занят. Если заняет, то кидаем ошибку с информацией о том, что токен уже использовался для
 * создания аккаунта.
 * 2. Проверяем, что токен связан с ролью студента. Если не связан с ролью, то кидаем ошибку о том, что токен
 * не является токеном студента.
 * 3. Проверяем, что пароль надежный. Если не надежный, то кидаем ошибку о том, что пароль не надежен.
 * 4. Находим указанную программу обучения. Если такой нет, то кидаем ошибку с информацией о том, что программа не
 * найдена.
 * 5. Создаем аккаунт пользователя.
 * 6. Создаем аккаунт студента.
 * 7. Отмечаем токен как использованный.
 */
public class CreateStudent {
    private final TokenGateway tokenGateway;
    private final LoggerGateway loggerGateway;
    private final PasswordGateway passwordGateway;
    private final UserGateway userGateway;
    private final ProgramGateway programGateway;
        private final StudentGateway studentGateway;

    public CreateStudent(
            TokenGateway tokenGateway,
            LoggerGateway loggerGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway,
            ProgramGateway programGateway,
            StudentGateway studentGateway) {
        this.tokenGateway = tokenGateway;
        this.loggerGateway = loggerGateway;
        this.passwordGateway = passwordGateway;
        this.userGateway = userGateway;
        this.programGateway = programGateway;
        this.studentGateway = studentGateway;
    }

    public void createStudent(CreateStudentRequest request) {
        UserRole role = tokenGateway
                .getRoleByToken(request.token())
                .orElseThrow(() -> new RegistrationTokenNotExistException(request.token()));
        ensureRole(role, request.token());

        ensurePasswordsSame(request.password(), request.passwordAgain());
        ensurePasswordStrongEnough(request.password());

        EducationalProgram educationalProgram = programGateway
                .findById(request.programId())
                .orElseThrow(() -> new EducationalProgramNotExistsException(request.programId()));

        User user = new User(request.token(), passwordGateway.encode(request.password()), role);
        userGateway.save(user);

        Student student = new Student(request.token(), educationalProgram, request.group(), user);
        studentGateway.save(student);

        tokenGateway.deleteToken(request.token());
    }

    private void ensureRole(UserRole current, UUID token) {
        if(current != UserRole.STUDENT) {
            if(current != null) {
                loggerGateway.warn("Student registration token " + token + " was used for " + current +
                        " registration");
            }
            throw new IncorrectUserRoleException(token);
        }
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
