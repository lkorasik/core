package ru.urfu.mm.application.usecase.createprogram;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Program;

import java.util.List;
import java.util.UUID;

public class CreateProgram {
    private final ProgramGateway programGateway;

    public CreateProgram(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public void createProgram(CreateProgramRequest request) {
        List<Integer> recommendedCredits = request.recommendedCredits();
        Program program = new Program(
                UUID.randomUUID(),
                request.name(),
                request.name(),
                List.of(),
                recommendedCredits.get(0),
                recommendedCredits.get(1),
                recommendedCredits.get(2),
                recommendedCredits.get(3));
        programGateway.save(program);
    }
}
