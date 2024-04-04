package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.ProgramGateway;
import ru.urfu.mm.domainlegacy.EducationalProgram;

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
