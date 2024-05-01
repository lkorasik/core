package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.persistance.entity.StudentEntity;
import ru.urfu.mm.persistance.repository.StudentRepository;

import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentEntity getStudent(String login) {
        UUID token = UUID.fromString(login);

        return studentRepository
                .findById(token)
                .orElseThrow();
    }
}
