package ru.urfu.mm.application.usecase.get_group;

import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.domain.AcademicGroup;

import java.util.UUID;

public class GetAcademicGroup {
    private final GroupGateway groupGateway;

    public GetAcademicGroup(GroupGateway groupGateway) {
        this.groupGateway = groupGateway;
    }

    public AcademicGroup getGroup(UUID groupId) {
        return groupGateway
                .findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }
}
