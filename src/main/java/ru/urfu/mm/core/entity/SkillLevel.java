package ru.urfu.mm.core.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SkillLevel {
    BEGINNER(0),
    INTERMEDIATE(1),
    ADVANCED(2);

    private int value;

    SkillLevel(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
