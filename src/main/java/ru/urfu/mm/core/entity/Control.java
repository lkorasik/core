package ru.urfu.mm.core.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Control {
    TEST("Test"),
    EXAM("Exam");

    private String value;

    Control(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
