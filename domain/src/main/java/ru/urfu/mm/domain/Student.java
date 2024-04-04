package ru.urfu.mm.domain;

import java.util.UUID;

public class Student {
    private UUID login;
    private EducationalProgram educationalProgram;
    private String group;
    private User user;

    public EducationalProgram getEducationalProgram() {
        return educationalProgram;
    }

    public void setEducationalProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

    public Student() {
    }

    public Student(UUID login, EducationalProgram educationalProgram, String group, User user) {
        this.login = login;
        this.educationalProgram = educationalProgram;
        this.group = group;
        this.user = user;
    }

    public UUID getLogin() {
        return login;
    }

    public String getGroup() {
        return group;
    }

    public User getUser() {
        return user;
    }
}
