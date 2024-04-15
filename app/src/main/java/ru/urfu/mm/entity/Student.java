package ru.urfu.mm.entity;

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
    @OneToOne
    @JoinColumn(name = "users_login")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "groups_id")
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public EducationalProgram getEducationalProgram() {
        return educationalProgram;
    }

    public void setEducationalProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

    public Student() {
    }

    public Student(UUID login, EducationalProgram educationalProgram, UserEntity userEntity) {
        this.login = login;
        this.educationalProgram = educationalProgram;
        this.userEntity = userEntity;
    }

    public UUID getLogin() {
        return login;
    }

    public UserEntity getUser() {
        return userEntity;
    }
}
