package ru.urfu.mm.persistance.entity.enums;

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

    public static UserEntityRole fromDomain(ru.urfu.mm.domain.enums.UserRole userRole) {
        return switch (userRole) {
            case STUDENT -> STUDENT;
            case ADMIN -> ADMIN;
        };
    }

    public static ru.urfu.mm.domain.enums.UserRole toDomain(UserEntityRole userEntityRole) {
        return switch (userEntityRole) {
            case STUDENT -> ru.urfu.mm.domain.enums.UserRole.STUDENT;
            case ADMIN -> ru.urfu.mm.domain.enums.UserRole.ADMIN;
        };
    }
}
