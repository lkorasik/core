package ru.urfu.mm.dsl;

import java.util.UUID;

/**
 * DSL для тестов
 */
public class DSL {
    public static String generatePassword() {
        return UUID.randomUUID().toString();
    }
}
