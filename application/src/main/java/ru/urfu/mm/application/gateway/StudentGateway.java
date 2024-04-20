package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Student;

import java.util.Optional;
import java.util.UUID;

public interface StudentGateway {
    void save(Student student);
    Student getById(UUID studentId);
    Optional<Student> findById(UUID studentId);
}
