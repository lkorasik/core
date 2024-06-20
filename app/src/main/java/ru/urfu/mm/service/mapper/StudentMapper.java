package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.Student;
import ru.urfu.mm.persistance.entity.StudentEntity;

@Service
public class StudentMapper {
    public StudentEntity toEntity(Student student) {
        return new StudentEntity();
    }
}
