package ru.urfu.mm.application.usecase.getgroup;

import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.domain.Group;

import java.util.UUID;

public class GetGroup {
    private final GroupGateway groupGateway;

    public GetGroup(GroupGateway groupGateway) {
        this.groupGateway = groupGateway;
    }

    public Group getGroup(UUID groupId) {
        return groupGateway
                .findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }
}
