package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.EducationalProgram;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.repository.EducationalProgramRepository;
import ru.urfu.mm.core.repository.SemesterRepository;
import ru.urfu.mm.core.repository.StudentRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EducationalProgramRepository educationalProgramRepository;

    public Student getStudent(String login) {
        UUID token = UUID.fromString(login);

        return studentRepository
                .findByLogin(token)
                .orElseThrow();
    }
}
