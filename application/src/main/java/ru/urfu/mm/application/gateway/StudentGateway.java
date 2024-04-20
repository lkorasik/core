package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Student;

import java.util.Optional;
import java.util.UUID;

public interface StudentGateway {
    /**
     * Используется для обновления сущности студента. Поле пользователя должно быть заполнено
     */
    void update(Student student);

    /**
     * Используется для создания сущности студента. Не сохраняет поля с группой и пользователем.
     */
    void saveNewStudent(Student student);
    Student getById(UUID studentId);
    Optional<Student> findById(UUID studentId);
}
