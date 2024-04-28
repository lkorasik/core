package ru.urfu.mm.application.usecase.create_program;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Program;

import java.util.UUID;

public class CreateProgram {
    private final ProgramGateway programGateway;

    public CreateProgram(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public void createProgram(CreateProgramRequest request) {
        Program program = new Program(
                UUID.randomUUID(),
                request.name(),
                request.trainingDirection());
        programGateway.save(program);
    }
}
