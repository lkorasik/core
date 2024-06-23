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
    private final GroupRepository groupRepository;
    private final AccountMapper userMapper;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentGatewayImpl(
            StudentRepository studentRepository,
            GroupRepository groupRepository,
            AccountMapper userMapper,
            StudentMapper studentMapper
    ) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.userMapper = userMapper;
        this.studentMapper = studentMapper;
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
        throw new ru.urfu.mm.application.exception.NotImplementedException();
//        return new EducationalProgramEntity(
//                educationalProgram.getId(),
//                educationalProgram.getName(),
//                educationalProgram.getTrainingDirection()
//        );
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
//        return studentRepository.findById(studentId)
//                .map(x -> {
//                            Account account = null;
//                            if (x.getUser() != null) {
//                                account = new Account(
//                                        x.getUser().getLogin(),
//                                        x.getUser().getPassword(),
//                                        UserEntityRole.toDomain(x.getUser().getRole())
//                                );
//                            }
//                            return new Student(
//                                    x.getId(),
//                                    account,
//                                    null,
//                                    null);
//                        }
//                );
    }

    @Override
    public List<Student> findAllStudentsByGroup(AcademicGroup academicGroup) {
        throw new ru.urfu.mm.application.exception.NotImplementedException();
//        academicGroup.getStudents();
//        throw new NotImplementedException();
    }

    @Override
    public void saveGroupStudents(List<Student> students, AcademicGroup group) {
//        GroupEntity groupEntity = new GroupEntity(group.getId(), group.getNumber(), Years.fromDomain(group.getYear()));
//        List<StudentEntity> studentEntities = students.stream()
//                .map(x -> new StudentEntity(x.getId(), groupEntity))
//                .toList();
//        studentRepository.saveAll(studentEntities);
        throw new NotImplementedException();
    }

    @Override
    public void update(Student student, Account account, AcademicGroup group) {
        throw new ru.urfu.mm.application.exception.NotImplementedException();
//        GroupEntity groupEntity = new GroupEntity(group.getId(), group.getNumber(), Years.fromDomain(group.getYear()));
//        StudentEntity entity = new StudentEntity(student.getId(), groupEntity);
//        entity.setAccount(new AccountEntity(account.token(), account.password(), UserEntityRole.fromDomain(account.role())));
//        studentRepository.save(entity);
    }
}
