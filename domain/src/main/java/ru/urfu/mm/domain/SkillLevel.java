package ru.urfu.mm.domain;

public enum SkillLevel {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced");

    private String value;

    SkillLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
