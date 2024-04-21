package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.application.usecase.create.user.CreateUserRequest;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.Student;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.domain.UserRole;

/**
 * Создаем аккаунт студента
 * 1. Находим студента.
 * 3. Находим программу.
 * 4. Создаем аккаунт пользователя.
 * 5. Заполняем студента.
 * 6. Сохраняем изменения в студенте.
 */
public class CreateStudent implements CreateUseCase {
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

    @Override
    public void create(CreateUserRequest request) {
        Student student = studentGateway.findById(request.token())
                .orElseThrow(() -> new RegistrationTokenNotExistException(request.token()));

        Program program = student.getEducationalProgram();

        User user = new User(request.token(), passwordGateway.encode(request.password()), UserRole.STUDENT);
        userGateway.save(user);

        Student completedStudent = new Student(request.token(), program, student.getGroup(), user);
        studentGateway.update(completedStudent);
    }
}
