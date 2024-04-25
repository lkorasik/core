package ru.urfu.mm.application.dsl;

import ru.urfu.mm.domain.Program;

import java.util.UUID;

public class ProgramDSL {
    public static Program create() {
        return new Program(
                UUID.randomUUID(),
                DSL.generateString(),
                DSL.generateString()
        );
    }
}
