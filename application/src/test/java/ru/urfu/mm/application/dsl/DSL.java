package ru.urfu.mm.application.dsl;

import java.util.UUID;

public class DSL {
    public static String generateString() {
        return UUID.randomUUID().toString();
    }
}
