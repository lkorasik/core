package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.entity.GroupEntity;
import ru.urfu.mm.entity.Years;
import ru.urfu.mm.repository.GroupRepository;

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
    public void save(Group group) {
        GroupEntity entity = new GroupEntity(group.getId(), group.getNumber(), Years.values()[group.getYear().ordinal()]);
        groupRepository.save(entity);
    }

    @Override
    public Optional<Group> findById(UUID groupId) {
        return groupRepository
                .findById(groupId)
                .map(x -> new Group(x.getId(), x.getNumber()));
    }
}
