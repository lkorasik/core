package ru.urfu.mm.application.usecase.get_program_by_id;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.EducationalProgram;

import java.util.UUID;

public class GetProgramById {
    private final ProgramGateway programGateway;

    public GetProgramById(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public EducationalProgram getProgramById(UUID programId) {
        return programGateway.getById(programId);
    }
}
