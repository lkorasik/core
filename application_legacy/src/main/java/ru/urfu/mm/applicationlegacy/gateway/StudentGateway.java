package ru.urfu.mm.applicationlegacy.gateway;

import ru.urfu.mm.domainlegacy.Student;

import java.util.UUID;

public interface StudentGateway {
    void save(Student student);
    Student getById(UUID studentId);
}
