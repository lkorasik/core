package ru.urfu.mm.core.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column
    private UUID login;
    @ManyToOne
    @JoinColumn(name = "educational_program_id")
    private EducationalProgram educationalProgram;
    @Column(name = "`group`")
    private String group;
    @OneToOne
    @JoinColumn(name = "users_login")
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
