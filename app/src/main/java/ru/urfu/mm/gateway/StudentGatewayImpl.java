package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.Student;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.StudentEntity;
import ru.urfu.mm.entity.UserEntity;
import ru.urfu.mm.repository.StudentRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.UUID;

@Component
public class StudentGatewayImpl implements StudentGateway {
    private final StudentRepository studentRepository;
    private final Mapper<ru.urfu.mm.domain.User, UserEntity> userMapper;

    @Autowired
    public StudentGatewayImpl(StudentRepository studentRepository, Mapper<ru.urfu.mm.domain.User, UserEntity> userMapper) {
        this.studentRepository = studentRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void save(Student student) {
        StudentEntity studentEntity1 = new StudentEntity(
                student.getLogin(),
                parse(student.getEducationalProgram()),
                userMapper.map(student.getUser())
        );
        studentRepository.save(studentEntity1);
    }

    @Override
    public Student getById(UUID studentId) {
        return studentRepository
                .findByLogin(studentId)
                .map(x -> new Student(
                        x.getLogin(),
                        new Program(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection(),
                                x.getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                        ),
                        x.getGroup().getNumber(),
                        new ru.urfu.mm.domain.User(
                                x.getUser().getLogin(),
                                x.getUser().getPassword(),
                                ru.urfu.mm.domain.UserRole.values()[x.getUser().getRole().ordinal()]
                        )
                ))
                .get();
    }

    private EducationalProgram parse(Program program) {
        return new EducationalProgram(
                program.getId(),
                program.getName(),
                program.getTrainingDirection(),
                program.getSemesterIdToRequiredCreditsCount()
        );
    }
}
