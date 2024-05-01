package ru.urfu.mm.domain.enums;

public enum SkillLevel {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced");

    private final String value;

    SkillLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
