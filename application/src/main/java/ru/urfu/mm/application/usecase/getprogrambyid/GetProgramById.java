package ru.urfu.mm.application.usecase.getprogrambyid;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Program;

import java.util.UUID;

public class GetProgramById {
    private final ProgramGateway programGateway;

    public GetProgramById(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public Program getProgramById(UUID programId) {
        return programGateway.getById(programId);
    }
}
