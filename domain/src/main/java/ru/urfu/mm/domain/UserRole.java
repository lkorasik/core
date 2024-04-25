package ru.urfu.mm.domain;


public enum UserRole {
    STUDENT("Student"),
    ADMIN("Admin");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
