package ru.urfu.mm.application.usecase.create.student;

import ru.urfu.mm.application.gateway.*;
import ru.urfu.mm.application.usecase.create.DifferentPasswordException;
import ru.urfu.mm.application.usecase.create.RegistrationTokenNotExistException;
import ru.urfu.mm.application.usecase.create.TooShortPasswordException;
import ru.urfu.mm.domain.*;

/**
 * Создаем аккаунт студента
 * 1. Проверяем, что есть такой студент. Если такого студента нет, то кидаем ошбику.
 * 2. Проверяем, что студент частично проинициализиован. Т.е. есть поля группы и направления, но нет полей с паролем.
 * 3. Проверяем, что пароль надежный. Если не надежный, то кидаем ошибку о том, что пароль не надежен.
 * 4. Создаем аккаунт пользователя.
 * 5. Заполняем студента.
 */
public class NewCreateStudent implements CreateStudent {
    private final TokenGateway tokenGateway;
    private final PasswordGateway passwordGateway;
    private final UserGateway userGateway;
    private final ProgramGateway programGateway;
    private final StudentGateway studentGateway;

    public NewCreateStudent(
            TokenGateway tokenGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway,
            ProgramGateway programGateway,
            StudentGateway studentGateway) {
        this.tokenGateway = tokenGateway;
        this.passwordGateway = passwordGateway;
        this.userGateway = userGateway;
        this.programGateway = programGateway;
        this.studentGateway = studentGateway;
    }

    @Override
    public void createStudent(CreateStudentRequest request) {
        Group group = tokenGateway.getStudentRegistrationToken(request.token())
                .orElseThrow(() -> new RegistrationTokenNotExistException(request.token()));

        tokenGateway.deleteToken(request.token());

        ensurePasswordsSame(request.password(), request.passwordAgain());
        ensurePasswordStrongEnough(request.password());

        Program program = programGateway
                .findByGroup(group)
                .orElseThrow(() -> new EducationalProgramNotExistsException(request.programId()));

        User user = new User(request.token(), passwordGateway.encode(request.password()), UserRole.STUDENT);
        userGateway.save(user);

        Student student = new Student(request.token(), program, request.group(), user);
        studentGateway.save(student);

        // todo: возможно, стоит отмечать токен как использованный, а не удалять его.
        tokenGateway.deleteStudentRegistrationToken(request.token());
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
