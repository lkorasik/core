package ru.urfu.mm.applicationlegacy.dsl;

import java.util.UUID;

public class DSL {
    public static String generateString() {
        return UUID.randomUUID().toString();
    }
}
