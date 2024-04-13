package ru.urfu.mm.application.dsl;

import ru.urfu.mm.domain.Module;

import java.util.UUID;

public class ModuleDSL {
    public static Module create() {
        return new Module(
                UUID.randomUUID(),
                DSL.generateString()
        );
    }
}
