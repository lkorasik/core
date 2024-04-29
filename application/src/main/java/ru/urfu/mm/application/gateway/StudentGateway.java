package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentGateway {
    Student getById(UUID studentId);
    Optional<Student> findById(UUID studentId);
    List<Student> findAllStudentsByGroup(AcademicGroup academicGroup);
    void saveGroupStudents(List<Student> students, AcademicGroup academicGroup);
}
