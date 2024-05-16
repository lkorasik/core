package ru.urfu.mm.application.dsl;

import java.util.UUID;

public class DSL {
    public static String generateString() {
        return UUID.randomUUID().toString();
    }

    public static String generateStrongPassword() {
        return UUID.randomUUID().toString();
    }

    public static String generateWeakPassword() {
        return UUID.randomUUID().toString().substring(0, 4);
    }
}
