package ru.urfu.mm.application.dsl;

import ru.urfu.mm.domain.EducationalProgram;

import java.util.UUID;

public class ProgramDSL {
    public static EducationalProgram create() {
        return new EducationalProgram(
                UUID.randomUUID(),
                DSL.generateString(),
                DSL.generateString(),
                "{}"
        );
    }
}
