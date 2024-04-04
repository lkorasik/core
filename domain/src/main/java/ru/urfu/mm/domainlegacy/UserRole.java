package ru.urfu.mm.domainlegacy;


public enum UserRole {
    STUDENT("Student"),
    UNIVERSITY_EMPLOYEE("UniversityEmployee"),
    ADMIN("Admin");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
