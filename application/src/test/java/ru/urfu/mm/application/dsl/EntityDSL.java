package ru.urfu.mm.application.dsl;

import ru.urfu.mm.domain.Program;

import java.util.UUID;

public class EntityDSL {
    public static String generateString() {
        return String.valueOf(UUID.randomUUID());
    }
}
