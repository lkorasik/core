package ru.urfu.mm.application.usecase.create_educational_program;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.EducationalProgram;

import java.util.UUID;

public class CreateEducationalProgram {
    private final ProgramGateway programGateway;

    public CreateEducationalProgram(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public void createProgram(CreateProgramRequest request) {
        EducationalProgram educationalProgram = new EducationalProgram(
                UUID.randomUUID(),
                request.name(),
                request.trainingDirection());
        programGateway.save(educationalProgram);
    }
}
