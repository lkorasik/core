package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.Group;

import java.util.Optional;
import java.util.UUID;

public interface GroupGateway {
    void save(Group group);
    Optional<Group> findById(UUID groupId);
}
