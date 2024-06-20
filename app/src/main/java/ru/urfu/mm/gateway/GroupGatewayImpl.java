package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.Account;
import ru.urfu.mm.domain.Student;
import ru.urfu.mm.domain.enums.UserRole;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.entity.enums.Years;
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GroupGatewayImpl implements GroupGateway {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GroupGatewayImpl(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void save(AcademicGroup academicGroup) {
        throw new NotImplementedException();
//        GroupEntity entity = new GroupEntity(academicGroup.getId(), academicGroup.getNumber(), Years.fromDomain(academicGroup.getYear()));
//        groupRepository.save(entity);
    }

    @Override
    public Optional<AcademicGroup> findById(UUID groupId) {
        throw new NotImplementedException();
//        GroupEntity entity = groupRepository.findById(groupId).get();
//        AcademicGroup academicGroup = new AcademicGroup(
//                entity.getId(),
//                entity.getNumber(),
//                ru.urfu.mm.domain.enums.Years.values()[entity.getYear().ordinal()]
//        );
//        List<Student> students = entity.getStudents()
//                .stream()
//                .map(x -> {
//                    Account account = null;
//                    if (x.getUser() != null) {
//                        account = new Account(x.getUser().getLogin(), x.getUser().getPassword(), UserRole.values()[x.getUser().getRole().ordinal()]);
//                    }
//                    return new Student(x.getId(), account, null, null);
//                })
//                .toList();
//        academicGroup.getStudents().addAll(students);
//        return Optional.of(academicGroup);
    }

    @Override
    public AcademicGroup findByStudent(Student student) {
        throw new NotImplementedException();
//        GroupEntity entity = studentRepository.findById(student.getId()).get().getGroup();
//        return new AcademicGroup(entity.getId(), entity.getNumber(), Years.toDomain(entity.getYear()));
    }
}
