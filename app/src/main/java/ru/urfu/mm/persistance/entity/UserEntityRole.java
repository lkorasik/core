package ru.urfu.mm.persistance.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserEntityRole {
    STUDENT("Student"),
    ADMIN("Admin");

    private final String value;

    UserEntityRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
