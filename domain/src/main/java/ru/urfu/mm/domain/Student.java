package ru.urfu.mm.domain;

import java.util.UUID;

public class Student {
    private UUID login;
    @Deprecated
    private Program program;
    @Deprecated
    private Group group;
    private Account account;

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Student() {
    }

    public Student(UUID login, Program program, Group group, Account account) {
        this.login = login;
        this.program = program;
        this.group = group;
        this.account = account;
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

    public Account getUser() {
        return account;
    }
}
