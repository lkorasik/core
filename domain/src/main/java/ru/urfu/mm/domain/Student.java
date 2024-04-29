package ru.urfu.mm.domain;

import java.util.UUID;

public class Student {
    private UUID login;
    @Deprecated
    private EducationalProgram educationalProgram;
    @Deprecated
    private AcademicGroup academicGroup;
    private Account account;

    public EducationalProgram getProgram() {
        return educationalProgram;
    }

    public void setProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

    public Student() {
    }

    public Student(UUID login, EducationalProgram educationalProgram, AcademicGroup academicGroup, Account account) {
        this.login = login;
        this.educationalProgram = educationalProgram;
        this.academicGroup = academicGroup;
        this.account = account;
    }

    public Student(UUID login, EducationalProgram educationalProgram, AcademicGroup academicGroup) {
        this.login = login;
        this.educationalProgram = educationalProgram;
        this.academicGroup = academicGroup;
    }

    public UUID getLogin() {
        return login;
    }

    public AcademicGroup getGroup() {
        return academicGroup;
    }

    public Account getUser() {
        return account;
    }
}
