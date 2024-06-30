package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.Student;
import ru.urfu.mm.persistance.entity.StudentEntity;

@Service
public class StudentMapper {
    private final AccountMapper accountMapper;

    @Autowired
    public StudentMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public StudentEntity toEntity(Student student) {
        return new StudentEntity(student.getId());
    }

    public Student toDomain(StudentEntity entity) {
        return new Student(
                entity.getId(),
                entity.getUser() == null ? null : accountMapper.toDomain(entity.getUser()),
                null
        );
    }
}
