package ru.urfu.mm.persistance.entity;

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
    private EducationalProgramEntity educationalProgramEntity;
    @OneToOne
    @JoinColumn(name = "users_login")
    private AccountEntity accountEntity;
    @ManyToOne
//    @JoinColumn(name = "groups_id")
    private GroupEntity group;

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public EducationalProgramEntity getEducationalProgram() {
        return educationalProgramEntity;
    }

    public void setEducationalProgram(EducationalProgramEntity educationalProgramEntity) {
        this.educationalProgramEntity = educationalProgramEntity;
    }

    public StudentEntity() {
    }

    public StudentEntity(UUID login, EducationalProgramEntity educationalProgramEntity, GroupEntity group, AccountEntity accountEntity) {
        this.login = login;
        this.educationalProgramEntity = educationalProgramEntity;
        this.group = group;
        this.accountEntity = accountEntity;
    }

    public StudentEntity(UUID login, EducationalProgramEntity educationalProgramEntity, GroupEntity group) {
        this.login = login;
        this.educationalProgramEntity = educationalProgramEntity;
        this.group = group;
    }

    public UUID getLogin() {
        return login;
    }

    public AccountEntity getUser() {
        return accountEntity;
    }
}
