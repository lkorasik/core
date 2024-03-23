package ru.urfu.mm.application.usecase;

import ru.urfu.mm.application.exception.EmptyProgramNameException;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Program;

public class CreateProgram {
    private final ProgramGateway programGateway;

    public CreateProgram(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public void createProgram(String name) {
        if (name.isBlank()) {
            throw new EmptyProgramNameException();
        }
        Program program = new Program(name);
        programGateway.saveProgram(program);
    }
}
