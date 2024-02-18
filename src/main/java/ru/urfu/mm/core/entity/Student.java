package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column
    private UUID login;
    @Column
    private UUID educationalProgramId;
    @Column(name = "`group`")
    private String group;
    @OneToOne
    @JoinColumn(name = "users_login")
    private User user;

    public Student() {
    }

    public Student(UUID login, UUID educationalProgramId, String group, User user) {
        this.login = login;
        this.educationalProgramId = educationalProgramId;
        this.group = group;
        this.user = user;
    }

    public UUID getLogin() {
        return login;
    }

    public UUID getEducationalProgramId() {
        return educationalProgramId;
    }

    public String getGroup() {
        return group;
    }

    public User getUser() {
        return user;
    }
}
