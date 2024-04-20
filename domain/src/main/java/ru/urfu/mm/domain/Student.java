package ru.urfu.mm.domain;

import java.util.UUID;

public class Student {
    private UUID login;
    private Program program;
    private Group group;
    private User user;

    public Program getEducationalProgram() {
        return program;
    }

    public void setEducationalProgram(Program program) {
        this.program = program;
    }

    public Student() {
    }

    public Student(UUID login, Program program, Group group, User user) {
        this.login = login;
        this.program = program;
        this.group = group;
        this.user = user;
    }

    public Student(UUID login, Program program, Group group) {
        this.login = login;
        this.program = program;
        this.group = group;
    }

    public UUID getLogin() {
        return login;
    }

    public Group getGroup() {
        return group;
    }

    public User getUser() {
        return user;
    }
}
