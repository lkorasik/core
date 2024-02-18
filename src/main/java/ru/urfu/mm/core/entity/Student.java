package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column
    public UUID login;
    @Column
    public UUID educationalProgramId;
    @Column(name = "`group`")
    public String group;
    @OneToOne
    @JoinColumn(name = "users_login")
    public User user;

    public Student() {
    }

    public Student(UUID login, UUID educationalProgramId, String group, User user) {
        this.login = login;
        this.educationalProgramId = educationalProgramId;
        this.group = group;
        this.user = user;
    }
}
