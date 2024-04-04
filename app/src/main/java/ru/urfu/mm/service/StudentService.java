package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.repository.StudentRepository;

import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudent(String login) {
        UUID token = UUID.fromString(login);

        return studentRepository
                .findByLogin(token)
                .orElseThrow();
    }
}
