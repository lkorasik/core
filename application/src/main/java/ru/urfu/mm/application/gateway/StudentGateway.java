package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Student;

import java.util.UUID;

public interface StudentGateway {
    void save(Student student);
    Student getById(UUID studentId);
}
