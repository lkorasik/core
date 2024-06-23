package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Student;
import ru.urfu.mm.domain.enums.UserRole;
import ru.urfu.mm.domain.exception.NotImplementedException;
import ru.urfu.mm.persistance.entity.*;
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.StudentRepository;
import ru.urfu.mm.service.mapper.AccountMapper;
import ru.urfu.mm.service.mapper.StudentMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class StudentGatewayImpl implements StudentGateway {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentGatewayImpl(
            StudentRepository studentRepository,
            StudentMapper studentMapper
    ) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public Student getById(UUID studentId) {
        throw new NotImplementedException();
    }

    @Override
    public void save(Student student) {
        StudentEntity entity = studentMapper.toEntity(student);
        studentRepository.save(entity);
    }

    @Override
    public Optional<Student> findById(UUID studentId) {
        throw new ru.urfu.mm.application.exception.NotImplementedException();
    }
}
