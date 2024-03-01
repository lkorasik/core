package ru.urfu.mm.entity;

import jakarta.persistence.*;
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
}
