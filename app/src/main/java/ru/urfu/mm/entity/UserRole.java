package ru.urfu.mm.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    STUDENT("Student"),
    UNIVERSITY_EMPLOYEE("UniversityEmployee"),
    ADMIN("Admin");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
