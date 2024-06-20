package ru.urfu.mm.application.usecase.get_groups;

import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.ProgramGateway;

import java.util.List;
import java.util.UUID;

/**
 * Получить список групп для программы
 */
public class GetGroupsByEducationalProgram {
    private final ProgramGateway programGateway;

    public GetGroupsByEducationalProgram(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public List<GroupResponse> getGroupsByEducationalProgram(UUID programId) {
        throw new NotImplementedException();
//        return programGateway.getById(programId)
//                .getGroups()
//                .stream()
//                .map(x -> new GroupResponse(x.getId(), x.getNumber()))
//                .toList();
    }
}
