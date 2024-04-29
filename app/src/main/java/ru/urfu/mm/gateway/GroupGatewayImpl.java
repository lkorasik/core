package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.entity.enums.Years;
import ru.urfu.mm.persistance.repository.GroupRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class GroupGatewayImpl implements GroupGateway {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupGatewayImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void save(AcademicGroup academicGroup) {
        GroupEntity entity = new GroupEntity(academicGroup.getId(), academicGroup.getNumber(), Years.values()[academicGroup.getYear().ordinal()]);
        groupRepository.save(entity);
    }

    @Override
    public Optional<AcademicGroup> findById(UUID groupId) {
        return groupRepository
                .findById(groupId)
                .map(x -> new AcademicGroup(x.getId(), x.getNumber()));
    }
}
