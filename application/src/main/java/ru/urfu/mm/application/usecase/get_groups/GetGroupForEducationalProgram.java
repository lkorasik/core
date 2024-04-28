package ru.urfu.mm.application.usecase.get_groups;

import ru.urfu.mm.application.gateway.ProgramGateway;

import java.util.List;
import java.util.UUID;

public class GetGroupForEducationalProgram {
    private final ProgramGateway programGateway;

    public GetGroupForEducationalProgram(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public List<GroupResponse> getGroupForEducationalProgram(UUID programId) {
        return programGateway.getById(programId)
                .getGroups()
                .stream()
                .map(x -> new GroupResponse(x.getId(), x.getNumber()))
                .toList();
    }
}
