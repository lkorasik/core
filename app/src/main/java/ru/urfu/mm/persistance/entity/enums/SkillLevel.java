package ru.urfu.mm.persistance.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SkillLevel {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced");

    private String value;

    SkillLevel(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static SkillLevel fromDomain(ru.urfu.mm.domain.enums.SkillLevel skillLevel) {
        return switch (skillLevel) {
            case BEGINNER -> BEGINNER;
            case INTERMEDIATE -> INTERMEDIATE;
            case ADVANCED -> ADVANCED;
        };
    }
}
