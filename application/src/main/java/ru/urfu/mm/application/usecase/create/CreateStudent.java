package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.application.gateway.PasswordGateway;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.application.usecase.create.account.CreateAccountRequest;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.domain.Student;
import ru.urfu.mm.domain.enums.UserRole;

/**
 * Создаем аккаунт студента
 * 1. Находим студента. Если студент не найден, то кидаем ошибку о том, что нет такого токена для регистрации.
 * 2. Создаем аккаунт пользователя.
 * 3. Заполняем студента.
 * 4. Сохраняем изменения в студенте.
 */
public class CreateStudent implements CreateUseCase {
    private final StudentGateway studentGateway;
    private final PasswordGateway passwordGateway;
    private final UserGateway userGateway;
    private final GroupGateway groupGateway;

    public CreateStudent(
            StudentGateway studentGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway,
            GroupGateway groupGateway) {
        this.studentGateway = studentGateway;
        this.passwordGateway = passwordGateway;
        this.userGateway = userGateway;
        this.groupGateway = groupGateway;
    }

    @Override
    public void create(CreateAccountRequest request) {
        Student student = studentGateway.findById(request.getToken())
                .orElseThrow(() -> new RegistrationTokenNotExistException(request.getToken()));

        Account account = new Account(request.getToken(), passwordGateway.encode(request.getPassword()), UserRole.STUDENT);
        userGateway.save(account);

        AcademicGroup group = groupGateway.findByStudent(student);

        Student completeStudent = new Student(request.getToken(), account, null, null);
        studentGateway.update(completeStudent, account, group);
    }
}
