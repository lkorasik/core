package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.repository.StudentRepository;

import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public Student getStudent(String login) {
        UUID token = UUID.fromString(login);

        Student student = repository
                .findByLogin(token)
                .orElseThrow();

        return student;
    }
}
