package ru.urfu.mm.domain;

import java.util.UUID;

public class Student {
    private UUID login;
    @Deprecated
    private EducationalProgram educationalProgram;
    @Deprecated
    private Group group;
    private Account account;

    public EducationalProgram getProgram() {
        return educationalProgram;
    }

    public void setProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

    public Student() {
    }

    public Student(UUID login, EducationalProgram educationalProgram, Group group, Account account) {
        this.login = login;
        this.educationalProgram = educationalProgram;
        this.group = group;
        this.account = account;
    }

    public Student(UUID login, EducationalProgram educationalProgram, Group group) {
        this.login = login;
        this.educationalProgram = educationalProgram;
        this.group = group;
    }

    public UUID getLogin() {
        return login;
    }

    public Group getGroup() {
        return group;
    }

    public Account getUser() {
        return account;
    }
}
