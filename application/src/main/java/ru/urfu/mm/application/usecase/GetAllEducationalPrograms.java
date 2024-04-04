package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.EducationalProgram;

import java.util.List;

public class GetAllEducationalPrograms {
    private final ProgramGateway programGateway;

    public GetAllEducationalPrograms(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public List<EducationalProgram> getAllPrograms() {
        return programGateway.getAll();
    }
}
