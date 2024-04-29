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
import ru.urfu.mm.persistance.entity.enums.Years;
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.StudentRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class StudentGatewayImpl implements StudentGateway {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final Mapper<Account, AccountEntity> userMapper;

    @Autowired
    public StudentGatewayImpl(
            StudentRepository studentRepository,
            GroupRepository groupRepository,
            Mapper<Account, AccountEntity> userMapper) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.userMapper = userMapper;
    }

//    @Override
//    public void update(Student student) {
//        StudentEntity studentEntity1 = new StudentEntity(
//                student.getId(),
//                parse(student.getProgram()),
//                new GroupEntity(
//                        student.getGroup().getId(),
//                        student.getGroup().getNumber(),
//                        Years.values()[student.getGroup().getYear().ordinal()]
//                ),
//                userMapper.map(student.getAccount())
//        );
//        studentRepository.save(studentEntity1);
//    }

//    @Override
//    public void saveNewStudent(Student student) {
//        StudentEntity entity = new StudentEntity(
//                student.getId(),
//                parse(student.getProgram()),
//                new GroupEntity(
//                        student.getGroup().getId(),
//                        student.getGroup().getNumber(),
//                        Years.values()[student.getGroup().getYear().ordinal()]
//                )
//        );
//        studentRepository.save(entity);
//    }

//    @Override
//    public Student getById(UUID studentId) {
//        return studentRepository
//                .findByLogin(studentId)
//                .map(x -> new Student(
//                        x.getLogin(),
//                        new EducationalProgram(
//                                x.getEducationalProgram().getId(),
//                                x.getEducationalProgram().getName(),
//                                x.getEducationalProgram().getTrainingDirection()
//                        ),
//                        new AcademicGroup(
//                                x.getGroup().getId(),
//                                x.getGroup().getNumber()
//                        ),
//                        new Account(
//                                x.getUser().getLogin(),
//                                x.getUser().getPassword(),
//                                UserRole.values()[x.getUser().getRole().ordinal()]
//                        )
//                ))
//                .get();
//    }

//    @Override
//    public Optional<Student> findById(UUID studentId) {
//        return studentRepository
//                .findByLogin(studentId)
//                .map(x -> new Student(
//                        x.getLogin(),
//                        new EducationalProgram(
//                                x.getEducationalProgram().getId(),
//                                x.getEducationalProgram().getName(),
//                                x.getEducationalProgram().getTrainingDirection()
//                        ),
//                        new AcademicGroup(
//                                x.getGroup().getId(),
//                                x.getGroup().getNumber()
//                        )
//                ));
//    }

//    @Override
//    public List<Student> findAllStudentsByGroup(AcademicGroup academicGroup) {
//        GroupEntity groupEntity = groupRepository.findById(academicGroup.getId()).get();
//        return studentRepository.findAllByGroup(groupEntity)
//                .stream()
//                .map(x -> new Student(
//                        x.getLogin(),
//                        new EducationalProgram(
//                                x.getEducationalProgram().getId(),
//                                x.getEducationalProgram().getName(),
//                                x.getEducationalProgram().getTrainingDirection()
//                        ),
//                        new AcademicGroup(
//                                x.getGroup().getId(),
//                                x.getGroup().getNumber()
//                        ),
//                        mapUser(x)
//                ))
//                .toList();
//    }

    private Account mapUser(StudentEntity student) {
        if (student.getUser() != null) {
            return new Account(
                    student.getUser().getLogin(),
                    student.getUser().getPassword(),
                    UserRole.values()[student.getUser().getRole().ordinal()]
            );
        }
        return null;
    }

    private EducationalProgramEntity parse(EducationalProgram educationalProgram) {
        return new EducationalProgramEntity(
                educationalProgram.getId(),
                educationalProgram.getName(),
                educationalProgram.getTrainingDirection()
        );
    }

    @Override
    public Student getById(UUID studentId) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Student> findById(UUID studentId) {
        throw new NotImplementedException();
    }

    @Override
    public List<Student> findAllStudentsByGroup(AcademicGroup academicGroup) {
        academicGroup.getStudents();
        throw new NotImplementedException();
    }

    @Override
    public void saveGroupStudents(List<Student> students, AcademicGroup group) {
        GroupEntity groupEntity = new GroupEntity(group.getId(), group.getNumber(), Years.values()[group.getYear().ordinal()]);
        List<StudentEntity> studentEntities = students.stream()
                .map(x -> new StudentEntity(x.getId(), groupEntity))
                .toList();
        studentRepository.saveAll(studentEntities);
    }
}
