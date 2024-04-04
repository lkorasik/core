package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.domain.Student;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.User;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.repository.StudentRepository;

import java.util.UUID;

@Component
public class StudentGatewayImpl implements StudentGateway {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentGatewayImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void save(Student student) {
        ru.urfu.mm.entity.Student student1 = new ru.urfu.mm.entity.Student(
                student.getLogin(),
                parse(student.getEducationalProgram()),
                student.getGroup(),
                parse(student.getUser())
        );
        studentRepository.save(student1);
    }

    @Override
    public Student getById(UUID studentId) {
        return studentRepository
                .findByLogin(studentId)
                .map(x -> new Student(
                        x.getLogin(),
                        new ru.urfu.mm.domain.EducationalProgram(
                                x.getEducationalProgram().getId(),
                                x.getEducationalProgram().getName(),
                                x.getEducationalProgram().getTrainingDirection(),
                                x.getEducationalProgram().getSemesterIdToRequiredCreditsCount()
                        ),
                        x.getGroup(),
                        new ru.urfu.mm.domain.User(
                                x.getUser().getLogin(),
                                x.getUser().getPassword(),
                                ru.urfu.mm.domain.UserRole.values()[x.getUser().getRole().ordinal()]
                        )
                ))
                .get();
    }

    private User parse(ru.urfu.mm.domain.User user) {
        return new User(
                user.getLogin(),
                user.getPassword(),
                UserRole.values()[user.getRole().ordinal()]
        );
    }

    private EducationalProgram parse(ru.urfu.mm.domain.EducationalProgram educationalProgram) {
        return new EducationalProgram(
                educationalProgram.getId(),
                educationalProgram.getName(),
                educationalProgram.getTrainingDirection(),
                educationalProgram.getSemesterIdToRequiredCreditsCount()
        );
    }
}
