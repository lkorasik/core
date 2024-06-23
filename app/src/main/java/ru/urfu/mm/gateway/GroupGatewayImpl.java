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
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.StudentRepository;
import ru.urfu.mm.service.mapper.AcademicGroupMapper;
import ru.urfu.mm.service.mapper.AccountMapper;
import ru.urfu.mm.service.mapper.StudentMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GroupGatewayImpl implements GroupGateway {
    private final GroupRepository groupRepository;
    private final AcademicGroupMapper academicGroupMapper;
    private final StudentMapper studentMapper;

    @Autowired
    public GroupGatewayImpl(
            GroupRepository groupRepository,
            AcademicGroupMapper academicGroupMapper,
            StudentMapper studentMapper
    ) {
        this.groupRepository = groupRepository;
        this.academicGroupMapper = academicGroupMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public void save(AcademicGroup academicGroup) {
        GroupEntity entity = academicGroupMapper.toEntity(academicGroup);
        groupRepository.save(entity);
    }

    @Override
    public Optional<AcademicGroup> findById(UUID groupId) {
        GroupEntity entity = groupRepository.findById(groupId).get();
        AcademicGroup academicGroup = academicGroupMapper.toDomain(entity);
        List<Student> students = entity.getStudents()
                .stream()
                .map(studentMapper::toDomain)
                .toList();
        academicGroup.getStudents().addAll(students);
        return Optional.of(academicGroup);
    }

    @Override
    public AcademicGroup findByStudent(Student student) {
        throw new NotImplementedException();
//        GroupEntity entity = studentRepository.findById(student.getId()).get().getGroup();
//        return new AcademicGroup(entity.getId(), entity.getNumber(), Years.toDomain(entity.getYear()));
    }
}
