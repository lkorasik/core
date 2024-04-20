package ru.urfu.mm.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "students")
public class StudentEntity {
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
    private GroupEntity group;

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public EducationalProgram getEducationalProgram() {
        return educationalProgram;
    }

    public void setEducationalProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

    public StudentEntity() {
    }

    public StudentEntity(UUID login, EducationalProgram educationalProgram, UserEntity userEntity) {
        this.login = login;
        this.educationalProgram = educationalProgram;
        this.userEntity = userEntity;
    }

    public StudentEntity(UUID login, EducationalProgram educationalProgram) {
        this.login = login;
        this.educationalProgram = educationalProgram;
    }

    public UUID getLogin() {
        return login;
    }

    public UserEntity getUser() {
        return userEntity;
    }
}
