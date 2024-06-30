package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.application.exception.NotImplementedException;

/**
 * Создаем аккаунт студента
 * 1. Находим студента. Если студент не найден, то кидаем ошибку о том, что нет такого токена для регистрации.
 * 2. Создаем аккаунт пользователя.
 * 3. Заполняем студента.
 * 4. Сохраняем изменения в студенте.
 */
public class CreateStudent implements CreateUseCase {
    @Override
    public void create(CreateAccountRequest request) {
        throw new NotImplementedException();
//        Student student = studentGateway.findById(request.token())
//                .orElseThrow(() -> new RegistrationTokenNotExistException(request.token()));

//        Account account = new Account(request.token(), passwordGateway.encode(request.password()), UserRole.STUDENT);
//        userGateway.save(account);

//        AcademicGroup group = groupGateway.findByStudent(student);

//        Student completeStudent = new Student(request.token(), account, null, null);
//        studentGateway.update(completeStudent, account, group);
    }
}
